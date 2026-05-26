package com.example.giftapp.data.repository

import com.example.giftapp.data.local.GiftDao
import com.example.giftapp.domain.model.AudioBlock
import com.example.giftapp.domain.model.GiftEntity
import com.example.giftapp.domain.model.HeaderBlock
import com.example.giftapp.domain.model.ImageBlock
import com.example.giftapp.domain.model.RemoteGift
import com.example.giftapp.domain.model.TextBlock
import com.example.giftapp.domain.model.VideoBlock
import com.example.giftapp.fakes.stubDocumentGet
import com.example.giftapp.fakes.stubDocumentSet
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verifyOrder
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class GiftRepositoryImplTest {

    private lateinit var dao: GiftDao
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var auth: FirebaseAuth
    private lateinit var repo: GiftRepositoryImpl

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        firestore = mockk(relaxed = true)
        storage = mockk(relaxed = true)
        auth = mockk(relaxed = true)
        repo = GiftRepositoryImpl(dao, auth, firestore, storage)

        mockkStatic(android.net.Uri::class)
        every { android.net.Uri.parse(any()) } answers {
            mockk<android.net.Uri>(relaxed = true).also {
                every { it.toString() } returns firstArg<String>()
            }
        }
    }

    @After
    fun tearDown() {
        unmockkStatic(android.net.Uri::class)
    }


    @Test
    fun `fetchRemoteGift returns parsed RemoteGift when document exists`() = runTest {
        firestore.stubDocumentGet(
            collection = "gifts",
            documentId = "g1",
            data = mapOf(
                "id" to "g1",
                "title" to "Hello",
                "sender" to "alice",
                "timestamp" to 42L,
                "contentBlocks" to """[{"type":"text_body","order":0,"text":"hi"}]""",
            ),
        )

        val result = repo.fetchRemoteGift("g1")

        assertNotNull(result)
        assertEquals("g1", result!!.id)
        assertEquals("Hello", result.title)
        assertEquals("alice", result.sender)
        assertEquals(42L, result.timestamp)
        assertEquals(listOf(TextBlock(order = 0, text = "hi")), result.contentBlocks)
    }

    @Test
    fun `fetchRemoteGift returns null when document does not exist`() = runTest {
        firestore.stubDocumentGet(collection = "gifts", documentId = "missing", data = null)

        val result = repo.fetchRemoteGift("missing")

        assertNull(result)
    }

    @Test
    fun `fetchRemoteGift returns null when get throws`() = runTest {
        val docRef = mockk<DocumentReference>(relaxed = true)
        every { docRef.get() } returns Tasks.forException(RuntimeException("network down"))
        val colRef = mockk<CollectionReference>(relaxed = true)
        every { colRef.document("g1") } returns docRef
        every { firestore.collection("gifts") } returns colRef

        val result = repo.fetchRemoteGift("g1")

        assertNull(result)
    }


    @Test
    fun `fetchRemoteGift does not delete the remote document`() = runTest {
        val docRef = firestore.stubDocumentGet(
            collection = "gifts",
            documentId = "g1",
            data = mapOf(
                "id" to "g1",
                "title" to "Hi",
                "sender" to "a",
                "timestamp" to 1L,
                "contentBlocks" to "[]",
            ),
        )

        repo.fetchRemoteGift("g1")

        coVerify(exactly = 0) { docRef.delete() }
    }

    @Test
    fun `fetchRemoteGift defaults missing fields to empty values`() = runTest {
        firestore.stubDocumentGet(
            collection = "gifts",
            documentId = "g1",
            data = emptyMap(),
        )

        val result = repo.fetchRemoteGift("g1")

        assertNotNull(result)
        assertEquals("g1", result!!.id)
        assertEquals("", result.title)
        assertEquals("", result.sender)
        assertEquals(0L, result.timestamp)
        assertEquals(emptyList<Any>(), result.contentBlocks)
    }


    @Test
    fun `sendGift uploads media for image, video, and audio blocks`() = runTest {
        val docRef = firestore.stubDocumentSet(collection = "gifts", documentId = "g1")
        stubStorageUpload(downloadUrlByUploadOrder = listOf("https://cdn/img", "https://cdn/vid", "https://cdn/aud"))

        val remote = RemoteGift(
            id = "g1",
            title = "T",
            sender = "s",
            timestamp = 1L,
            contentBlocks = listOf(
                HeaderBlock(order = 0, text = "H"), // no upload expected
                ImageBlock(order = 1, url = "content://img"),
                VideoBlock(order = 2, url = "content://vid"),
                AudioBlock(order = 3, url = "content://aud"),
            ),
        )

        val ok = repo.sendGift(remote)

        assertTrue(ok)
        coVerify(exactly = 1) { docRef.set(any()) }
    }

    @Test
    fun `sendGift returns true on success`() = runTest {
        firestore.stubDocumentSet(collection = "gifts", documentId = "g1")

        val ok = repo.sendGift(RemoteGift(id = "g1", title = "T"))

        assertTrue(ok)
    }

    @Test
    fun `sendGift returns false when set throws`() = runTest {
        val docRef = mockk<DocumentReference>(relaxed = true)
        every { docRef.set(any()) } returns Tasks.forException(RuntimeException("nope"))
        val colRef = mockk<CollectionReference>(relaxed = true)
        every { colRef.document("g1") } returns docRef
        every { firestore.collection("gifts") } returns colRef

        val ok = repo.sendGift(RemoteGift(id = "g1", title = "T"))

        assertFalse(ok)
    }

    @Test
    fun `sendGift does not mutate the input ContentBlock url values`() = runTest {
        firestore.stubDocumentSet(collection = "gifts", documentId = "g1")
        stubStorageUpload(downloadUrlByUploadOrder = listOf("https://cdn/new"))

        val image = ImageBlock(order = 0, url = "content://original")
        val input = listOf<com.example.giftapp.domain.model.ContentBlock>(image)
        repo.sendGift(RemoteGift(id = "g1", title = "T", contentBlocks = input))
        assertEquals("content://original", image.url)
    }

    @Test
    fun `deleteRemoteGift deletes the document on Firestore`() = runTest {
        val docRef = firestore.stubDocumentGet(
            collection = "gifts",
            documentId = "g1",
            data = mapOf("id" to "g1"),
        )

        repo.deleteRemoteGift("g1")

        coVerify(exactly = 1) { docRef.delete() }
    }

    private fun stubStorageUpload(downloadUrlByUploadOrder: List<String>) {
        val rootRef = mockk<StorageReference>(relaxed = true)
        every { storage.reference } returns rootRef

        val urls = downloadUrlByUploadOrder.iterator()
        every { rootRef.child(any()) } answers {
            val nextUrl = urls.next()
            val childRef = mockk<StorageReference>(relaxed = true)

            val uploadTask = mockk<UploadTask>(relaxed = true)
            every { uploadTask.isComplete } returns true
            every { uploadTask.isSuccessful } returns true
            every { uploadTask.isCanceled } returns false
            every { uploadTask.exception } returns null
            every { uploadTask.result } returns mockk(relaxed = true)
            every { uploadTask.addOnCompleteListener(any()) } answers {
                @Suppress("UNCHECKED_CAST")
                val listener = firstArg<com.google.android.gms.tasks.OnCompleteListener<UploadTask.TaskSnapshot>>()
                listener.onComplete(uploadTask)
                uploadTask
            }
            every { childRef.putFile(any()) } returns uploadTask
            val uri = mockk<android.net.Uri>()
            every { uri.toString() } returns nextUrl
            every { childRef.downloadUrl } returns Tasks.forResult(uri)
            childRef
        }
    }
}

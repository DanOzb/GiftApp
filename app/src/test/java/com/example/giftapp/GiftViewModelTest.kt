package com.example.giftapp

import app.cash.turbine.test
import com.example.giftapp.domain.model.ContentBlock
import com.example.giftapp.domain.model.GiftEntity
import com.example.giftapp.domain.model.HeaderBlock
import com.example.giftapp.domain.model.RemoteGift
import com.example.giftapp.fakes.FakeGiftRepository
import com.example.giftapp.viewmodel.GiftViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GiftViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repo: FakeGiftRepository
    private lateinit var viewModel: GiftViewModel

    @Before
    fun setUp() {
        repo = FakeGiftRepository()
        viewModel = GiftViewModel(repo)
    }


    private fun gift(
        id: String,
        timestamp: Long = 0L,
        favorite: Boolean = false,
        blocks: List<ContentBlock> = emptyList(),
    ) = GiftEntity(
        id = id,
        title = "t-$id",
        sender = "s",
        timestamp = timestamp,
        favorite = favorite,
        contentBlocks = blocks,
    )


    @Test
    fun `gifts flow emits sorted by timestamp descending (newest first)`() = runTest {
        repo.seedGifts(
            gift(id = "zz-old", timestamp = 100L),
            gift(id = "aa-new", timestamp = 300L),
            gift(id = "mm-mid", timestamp = 200L),
        )

        viewModel.gifts.test {
            val emitted = awaitItem()
            assertEquals(
                listOf("aa-new", "mm-mid", "zz-old"),
                emitted.map { it.id },
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `favoriteGifts only contains gifts where favorite is true`() = runTest {
        repo.seedGifts(
            gift(id = "1", timestamp = 100L, favorite = false),
            gift(id = "2", timestamp = 200L, favorite = true),
            gift(id = "3", timestamp = 300L, favorite = true),
        )

        viewModel.favoriteGifts.test {
            val emitted = awaitItem()
            assertEquals(setOf("2", "3"), emitted.map { it.id }.toSet())
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `addGift delegates to repository`() = runTest {
        val g = gift(id = "new", timestamp = 1L)

        viewModel.addGift(g)
        advanceUntilIdle()

        assertEquals(listOf(g), repo.addedGifts)
    }

    @Test
    fun `deleteGift delegates to repository`() = runTest {
        val g = gift(id = "x", timestamp = 1L)
        repo.seedGifts(g)

        viewModel.deleteGift(g)
        advanceUntilIdle()

        assertEquals(listOf(g), repo.deletedGifts)
    }

    @Test
    fun `updateGift delegates to repository`() = runTest {
        val g = gift(id = "x", timestamp = 1L)
        repo.seedGifts(g)

        viewModel.updateGift(g.copy(favorite = true))
        advanceUntilIdle()

        assertEquals(1, repo.updatedGifts.size)
        assertTrue(repo.updatedGifts.single().favorite)
    }


    @Test
    fun `loadAndSaveGift on success saves entity locally and exposes it in openedGift`() = runTest {
        val remote = RemoteGift(
            id = "remote-1",
            title = "Hello",
            sender = "alice",
            timestamp = 42L,
            contentBlocks = listOf(HeaderBlock(order = 0, text = "Hi")),
        )
        repo.remoteGifts["remote-1"] = remote

        viewModel.loadAndSaveGift("remote-1").join()

        assertEquals(1, repo.addedGifts.size)
        assertEquals("remote-1", repo.addedGifts.single().id)

        val opened = viewModel.openedGift.value
        assertNotNull(opened)
        assertEquals("remote-1", opened!!.id)
        assertEquals("Hello", opened.title)
    }

    @Test
    fun `loadAndSaveGift when remote returns null clears openedGift`() = runTest {
        val previous = RemoteGift(id = "previous", title = "Old")
        repo.remoteGifts["previous"] = previous
        viewModel.loadAndSaveGift("previous").join()
        assertEquals("previous", viewModel.openedGift.value?.id)

        repo.remoteGifts.clear()

        viewModel.loadAndSaveGift("missing").join()
        assertNull(viewModel.openedGift.value)
        assertEquals("Nothing new should have been added to local store.",
            1, repo.addedGifts.size)
    }

    /**
     * Uses its OWN StandardTestDispatcher (overriding the rule) because this test
     * needs virtual-time control via advanceTimeBy(). The default UnconfinedTestDispatcher
     * runs everything inline and doesn't let us observe an in-flight job.
     */
    @Test
    fun `loadAndSaveGift returned Job completes only after save finishes`() {
        val dispatcher = StandardTestDispatcher()
        runTest(dispatcher) {
            Dispatchers.setMain(dispatcher)
            repo = FakeGiftRepository()
            viewModel = GiftViewModel(repo)

            repo.remoteGifts["slow"] = RemoteGift(id = "slow", title = "S")
            repo.fetchRemoteGiftDelayMs = 1_000L

            val job = viewModel.loadAndSaveGift("slow")

            advanceTimeBy(500L)
            assertFalse("Job should not be done yet", job.isCompleted)
            assertNull(viewModel.openedGift.value)

            job.join()
            assertTrue(job.isCompleted)
            assertEquals("slow", viewModel.openedGift.value?.id)
        }
    }


    @Test
    fun `sendGift result starts null, becomes true on success`() = runTest {
        repo.sendGiftReturns = true
        val remote = RemoteGift(id = "g", title = "G")

        viewModel.sendGiftResult.test {
            assertNull(awaitItem()) // initial

            viewModel.sendGift(remote)
            advanceUntilIdle()

            assertEquals(true, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        assertEquals(listOf(remote), repo.sendGiftCalls)
    }

    @Test
    fun `sendGift result becomes false on failure`() = runTest {
        repo.sendGiftReturns = false

        viewModel.sendGiftResult.test {
            assertNull(awaitItem()) // initial

            viewModel.sendGift(RemoteGift(id = "g"))
            advanceUntilIdle()

            assertEquals(false, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `resetSendGiftResult sets the flow back to null`() = runTest {
        repo.sendGiftReturns = true
        viewModel.sendGift(RemoteGift(id = "g"))
        advanceUntilIdle()
        assertEquals(true, viewModel.sendGiftResult.value)

        viewModel.resetSendGiftResult()

        assertNull(viewModel.sendGiftResult.value)
    }


    @Test
    fun `clearOpenedGift sets openedGift to null`() = runTest {
        repo.remoteGifts["g"] = RemoteGift(id = "g", title = "G")
        viewModel.loadAndSaveGift("g").join()
        assertNotNull(viewModel.openedGift.value)

        viewModel.clearOpenedGift()

        assertNull(viewModel.openedGift.value)
    }

    @Test
    fun `loadAndSaveGift deletes remote only after local insert succeeds`() = runTest {
        val remote = RemoteGift(id = "ok", title = "T")
        repo.remoteGifts["ok"] = remote

        viewModel.loadAndSaveGift("ok").join()

        assertEquals(1, repo.addedGifts.size)
        assertEquals(listOf("ok"), repo.deleteRemoteGiftCalls)
    }

    @Test
    fun `loadAndSaveGift does not delete remote when fetch returns null`() = runTest {
        repo.remoteGifts.clear()

        viewModel.loadAndSaveGift("missing").join()
        assertEquals(0, repo.addedGifts.size)
        assertEquals(emptyList<String>(), repo.deleteRemoteGiftCalls)
    }
}
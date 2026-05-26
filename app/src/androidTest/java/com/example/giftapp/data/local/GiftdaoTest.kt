package com.example.giftapp.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.giftapp.domain.model.AudioBlock
import com.example.giftapp.domain.model.ContentBlock
import com.example.giftapp.domain.model.FooterBlock
import com.example.giftapp.domain.model.GiftEntity
import com.example.giftapp.domain.model.HeaderBlock
import com.example.giftapp.domain.model.ImageBlock
import com.example.giftapp.domain.model.TextBlock
import com.example.giftapp.domain.model.VideoBlock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GiftDaoTest {

    private lateinit var db: GiftDatabase
    private lateinit var dao: GiftDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        db = Room.inMemoryDatabaseBuilder(context, GiftDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.giftDao()
    }

    @After
    fun tearDown() {
        db.close()
    }


    private fun gift(
        id: String,
        timestamp: Long = 0L,
        favorite: Boolean = false,
        blocks: List<ContentBlock> = emptyList(),
    ) = GiftEntity(
        id = id,
        title = "title-$id",
        sender = "sender-$id",
        timestamp = timestamp,
        favorite = favorite,
        contentBlocks = blocks,
    )


    @Test
    fun insert_then_read_returns_inserted_gift() = runTest {
        val g = gift(id = "g1", timestamp = 100L)

        dao.insert(g)

        val all = dao.getAllGifts().first()
        assertEquals(listOf(g), all)
    }

    @Test
    fun insert_with_existing_id_replaces_existing_row() = runTest {
        val original = gift(id = "same", timestamp = 1L).copy(title = "Original")
        val replacement = gift(id = "same", timestamp = 2L).copy(title = "Replacement")

        dao.insert(original)
        dao.insert(replacement)

        val all = dao.getAllGifts().first()
        assertEquals(1, all.size)
        assertEquals("Replacement", all.single().title)
        assertEquals(2L, all.single().timestamp)
    }

    @Test
    fun updateGift_updates_existing_row() = runTest {
        val g = gift(id = "fav", favorite = false)
        dao.insert(g)

        dao.updateGift(g.copy(favorite = true))

        val updated = dao.getAllGifts().first().single()
        assertTrue(updated.favorite)
    }

    @Test
    fun delete_removes_row() = runTest {
        val g = gift(id = "del")
        dao.insert(g)
        assertEquals(1, dao.getAllGifts().first().size)

        dao.delete(g)

        assertEquals(emptyList<GiftEntity>(), dao.getAllGifts().first())
    }

    @Test
    fun getAllGifts_on_empty_db_emits_empty_list() = runTest {
        val all = dao.getAllGifts().first()
        assertEquals(emptyList<GiftEntity>(), all)
    }

    @Test
    fun getFavoriteGifts_returns_only_favorites() = runTest {
        dao.insert(gift(id = "1", favorite = false))
        dao.insert(gift(id = "2", favorite = true))
        dao.insert(gift(id = "3", favorite = true))

        val favorites = dao.getFavoriteGifts().first()

        assertEquals(setOf("2", "3"), favorites.map { it.id }.toSet())
    }

    @Test
    fun getFavoriteGifts_empty_when_none_favorited() = runTest {
        dao.insert(gift(id = "1", favorite = false))
        dao.insert(gift(id = "2", favorite = false))

        assertEquals(emptyList<GiftEntity>(), dao.getFavoriteGifts().first())
    }

    @Test
    fun all_content_block_types_round_trip_through_room() = runTest {
        val blocks: List<ContentBlock> = listOf(
            HeaderBlock(order = 0, text = "Hi"),
            TextBlock(order = 1, text = "Body"),
            ImageBlock(order = 2, url = "http://i", caption = "cap"),
            VideoBlock(order = 3, url = "http://v"),
            AudioBlock(order = 4, url = "http://a"),
            FooterBlock(order = 5, text = "Bye"),
        )
        val g = gift(id = "blocks", blocks = blocks)

        dao.insert(g)

        val stored = dao.getAllGifts().first().single()
        assertEquals(blocks, stored.contentBlocks)
    }

    @Test
    fun audio_block_round_trips_through_room() = runTest {
        val g = gift(
            id = "audio-only",
            blocks = listOf(AudioBlock(order = 0, url = "http://a"))
        )

        dao.insert(g)

        val stored = dao.getAllGifts().first().single()
        assertEquals(listOf(AudioBlock(order = 0, url = "http://a")), stored.contentBlocks)
    }

    @Test
    fun empty_content_blocks_list_round_trips() = runTest {
        val g = gift(id = "empty-blocks", blocks = emptyList())

        dao.insert(g)

        val stored = dao.getAllGifts().first().single()
        assertEquals(emptyList<ContentBlock>(), stored.contentBlocks)
    }

    @Test
    fun getAllGifts_flow_re_emits_after_insert() = runTest {
        dao.insert(gift(id = "a"))
        assertEquals(1, dao.getAllGifts().first().size)

        dao.insert(gift(id = "b"))

        assertEquals(2, dao.getAllGifts().first().size)
    }

    @Test
    fun getFavoriteGifts_flow_re_emits_after_favorite_toggled() = runTest {
        val g = gift(id = "x", favorite = false)
        dao.insert(g)
        assertEquals(emptyList<GiftEntity>(), dao.getFavoriteGifts().first())

        dao.updateGift(g.copy(favorite = true))

        val favorites = dao.getFavoriteGifts().first()
        assertEquals(1, favorites.size)
        assertEquals("x", favorites.single().id)
    }
}
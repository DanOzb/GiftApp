package com.example.giftapp

import com.example.giftapp.domain.model.AudioBlock
import com.example.giftapp.domain.model.ContentBlock
import com.example.giftapp.domain.model.ContentBlocksConverter
import com.example.giftapp.domain.model.FooterBlock
import com.example.giftapp.domain.model.HeaderBlock
import com.example.giftapp.domain.model.ImageBlock
import com.example.giftapp.domain.model.TextBlock
import com.example.giftapp.domain.model.VideoBlock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ContentBlocksConverterTest {

    private lateinit var converter: ContentBlocksConverter

    @Before
    fun setUp() {
        converter = ContentBlocksConverter()
    }


    @Test
    fun `header block round trip preserves all fields`() {
        val original: List<ContentBlock> = listOf(HeaderBlock(order = 3, text = "Happy Birthday!"))
        val decoded = converter.toContentBlockList(converter.fromContentBlockList(original))
        assertEquals(original, decoded)
    }

    @Test
    fun `text block round trip preserves all fields`() {
        val original: List<ContentBlock> = listOf(
            TextBlock(
                order = 7,
                text = "Hope you have a great day"
            )
        )
        val decoded = converter.toContentBlockList(converter.fromContentBlockList(original))
        assertEquals(original, decoded)
    }

    @Test
    fun `image block round trip preserves url and caption`() {
        val original: List<ContentBlock> = listOf(
            ImageBlock(order = 1, url = "https://example.com/img.jpg", caption = "us on the beach")
        )
        val decoded = converter.toContentBlockList(converter.fromContentBlockList(original))
        assertEquals(original, decoded)
    }

    @Test
    fun `video block round trip preserves url`() {
        val original: List<ContentBlock> = listOf(
            VideoBlock(
                order = 2,
                url = "https://example.com/v.mp4"
            )
        )
        val decoded = converter.toContentBlockList(converter.fromContentBlockList(original))
        assertEquals(original, decoded)
    }

    @Test
    fun `footer block round trip preserves all fields`() {
        val original: List<ContentBlock> = listOf(FooterBlock(order = 9, text = "With love"))
        val decoded = converter.toContentBlockList(converter.fromContentBlockList(original))
        assertEquals(original, decoded)
    }

    @Test
    fun `audio block round trip preserves url`() {
        val original: List<ContentBlock> = listOf(
            AudioBlock(
                order = 4,
                url = "https://example.com/a.mp3"
            )
        )
        val decoded = converter.toContentBlockList(converter.fromContentBlockList(original))
        assertEquals(original, decoded)
    }


    @Test
    fun `empty list round trips to empty list`() {
        val decoded = converter.toContentBlockList(converter.fromContentBlockList(emptyList()))
        assertEquals(emptyList<ContentBlock>(), decoded)
    }

    @Test
    fun `mixed list preserves type identity and order field for every element`() {
        val original: List<ContentBlock> = listOf(
            HeaderBlock(order = 0, text = "H"),
            TextBlock(order = 1, text = "T"),
            ImageBlock(order = 2, url = "img", caption = "c"),
            VideoBlock(order = 3, url = "vid"),
            AudioBlock(order = 4, url = "aud"),
            FooterBlock(order = 5, text = "F"),
        )
        val decoded = converter.toContentBlockList(converter.fromContentBlockList(original))
        assertEquals(original, decoded)
    }

    @Test
    fun `list order is preserved as written, not sorted by order field`() {
        // The `order` field is for the renderer to sort by; the converter must not reorder.
        val original: List<ContentBlock> = listOf(
            TextBlock(order = 9, text = "second-by-order"),
            TextBlock(order = 1, text = "first-by-order"),
        )
        val decoded = converter.toContentBlockList(converter.fromContentBlockList(original))
        assertEquals("second-by-order", (decoded[0] as TextBlock).text)
        assertEquals("first-by-order", (decoded[1] as TextBlock).text)
    }

    @Test
    fun `encoded json contains type discriminator for each block`() {
        val json = converter.fromContentBlockList(
            listOf(
                HeaderBlock(text = "h"),
                TextBlock(text = "t"),
                ImageBlock(url = "i"),
                VideoBlock(url = "v"),
                AudioBlock(url = "a"),
                FooterBlock(text = "f"),
            )
        )
        assertTrue("missing header discriminator: $json",  json.contains("\"type\":\"header\""))
        assertTrue("missing text discriminator: $json",    json.contains("\"type\":\"text_body\""))
        assertTrue("missing image discriminator: $json",   json.contains("\"type\":\"image\""))
        assertTrue("missing video discriminator: $json",   json.contains("\"type\":\"video\""))
        assertTrue("missing audio discriminator: $json",   json.contains("\"type\":\"audio\""))
        assertTrue("missing footer discriminator: $json",  json.contains("\"type\":\"footer\""))
    }

    @Test
    fun `can decode legacy json with known discriminators`() {
        val json = """
            [
              {"type":"header","order":0,"text":"Hi"},
              {"type":"text_body","order":1,"text":"Body"},
              {"type":"image","order":2,"url":"http://i","caption":"cap"},
              {"type":"video","order":3,"url":"http://v"},
              {"type":"audio","order":4,"url":"http://a"},
              {"type":"footer","order":5,"text":"Bye"}
            ]
        """.trimIndent()

        val decoded = converter.toContentBlockList(json)

        assertEquals(6, decoded.size)
        assertEquals(HeaderBlock(order = 0, text = "Hi"),                                decoded[0])
        assertEquals(TextBlock(order = 1, text = "Body"),                                decoded[1])
        assertEquals(ImageBlock(order = 2, url = "http://i", caption = "cap"),           decoded[2])
        assertEquals(VideoBlock(order = 3, url = "http://v"),                            decoded[3])
        assertEquals(AudioBlock(order = 4, url = "http://a"),                            decoded[4])
        assertEquals(FooterBlock(order = 5, text = "Bye"),                               decoded[5])
    }

    @Test
    fun `unknown json keys are ignored on decode`() {
        val json = """
            [
              {"type":"text_body","order":0,"text":"hello","version":2,"extra":{"a":1}}
            ]
        """.trimIndent()

        val decoded = converter.toContentBlockList(json)

        assertEquals(listOf(TextBlock(order = 0, text = "hello")), decoded)
    }


    @Test
    fun `default-constructed blocks round trip with default values`() {
        val original: List<ContentBlock> = listOf(
            HeaderBlock(),
            TextBlock(),
            ImageBlock(),
            VideoBlock(),
            AudioBlock(),
            FooterBlock(),
        )
        val decoded = converter.toContentBlockList(converter.fromContentBlockList(original))
        assertEquals(original, decoded)
    }

    @Test
    fun `text containing json-sensitive characters survives round trip`() {
        val tricky = "Quote \" backslash \\ newline \n brace } bracket ]"
        val original: List<ContentBlock> = listOf(TextBlock(order = 0, text = tricky))
        val decoded = converter.toContentBlockList(converter.fromContentBlockList(original))
        assertEquals(tricky, (decoded[0] as TextBlock).text)
    }
}
package com.example.giftapp.domain.model

import androidx.room.TypeConverter
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.json.Json


class ContentBlocksConverter {

    private val json = Json {
        serializersModule = SerializersModule {
            polymorphic(ContentBlock::class) {
                subclass(HeaderBlock::class)
                subclass(TextBlock::class)
                subclass(ImageBlock::class)
                subclass(VideoBlock::class)
                subclass(FooterBlock::class)
            }
        }
        ignoreUnknownKeys = true
    }

    private val contentBlockListSerializer = ListSerializer(
        PolymorphicSerializer(ContentBlock::class)
    )

    @TypeConverter
    fun fromContentBlockList(blocks: List<ContentBlock>): String {
        return json.encodeToString(contentBlockListSerializer, blocks)
    }

    @TypeConverter
    fun toContentBlockList(jsonString: String): List<ContentBlock> {
        return json.decodeFromString(contentBlockListSerializer, jsonString)
    }
}
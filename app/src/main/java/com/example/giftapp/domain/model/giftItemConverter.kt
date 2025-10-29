package com.example.giftapp.domain.model

fun giftItemConverter(items: List<GiftItem>): List<ContentBlock>{
    return items.mapIndexedNotNull { index, item ->
        when (item) {
            is GiftItem.Header -> if (item.text.isNotBlank()) HeaderBlock(order = index, text = item.text) else null
            is GiftItem.Message -> if (item.text.isNotBlank()) TextBlock(order = index, text = item.text) else null
            is GiftItem.Footer -> if (item.text.isNotBlank()) FooterBlock(order = index, text = item.text) else null

            //TODO: Change uri to downloadable url for firebase cloud storage
            is GiftItem.Image -> item.uri?.let { ImageBlock(order = index, url = it.toString(), caption = "") }
            is GiftItem.Video -> item.uri?.let { VideoBlock(order = index, url = it.toString()) }
            is GiftItem.Audio -> item.uri?.let { AudioBlock(order = index, url = it.toString()) }
        }
    }
}
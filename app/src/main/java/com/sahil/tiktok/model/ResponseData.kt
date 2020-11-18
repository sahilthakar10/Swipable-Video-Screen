package com.sahil.tiktok.model

sealed class ResponseData {

    data class ERROR(val error: Throwable) : ResponseData()
    sealed class SUCCESS<out T>(val  response: T) : ResponseData() {
        data class VideoData(val res: MutableList<VideoDataModel>) : SUCCESS<MutableList<VideoDataModel>>(res)
    }
}
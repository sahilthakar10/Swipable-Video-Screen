package com.sahil.tiktok.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.sahil.tiktok.model.VideoDataModel
import java.io.IOException

object DataRepository {

    private var gson = Gson()
    private var jsonString = ""
    fun getVideoList(context: Context): MutableList<VideoDataModel>{
        try {
           jsonString = context.assets.open("videoList.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        return gson.fromJson(jsonString, Array<VideoDataModel>::class.java).toMutableList()
    }

}
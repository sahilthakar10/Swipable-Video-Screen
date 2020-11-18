package com.sahil.tiktok.home.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sahil.tiktok.model.ResponseData
import com.sahil.tiktok.repository.DataRepository

class VideoDataViewModel : ViewModel() {

    var response: MutableLiveData<ResponseData> = MutableLiveData()

    fun getVideoList(context: Context) = response.postValue(ResponseData.SUCCESS.VideoData(DataRepository.getVideoList(context)))

}
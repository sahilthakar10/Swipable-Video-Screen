package com.sahil.tiktok.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class VideoDataModel(
    val title: String,
    val fullName: String,
    val description: String,
    val url: String
): Parcelable
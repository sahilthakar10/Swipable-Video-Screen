package com.sahil.tiktok.extension

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun Context.logError(message: String) = Log.e(this::class.java.name, message)

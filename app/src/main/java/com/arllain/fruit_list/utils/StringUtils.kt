package com.arllain.fruit_list.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

@Throws(IllegalArgumentException::class)
fun String.convertToBitmap(): Bitmap? {
    val decodedBytes: ByteArray = Base64.decode(
        this.substring(this.indexOf(",") + 1),
        Base64.DEFAULT
    )
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}
package com.arllain.fruit_list.utils

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream


/**
 * reduces the size of the image
 * @param maxSize38
 *
 * @return
 */
fun Bitmap.getResizedBitmap(maxSize: Int): Bitmap? {
    var width = this.width
    var height = this.height
    val bitmapRatio = width.toFloat() / height.toFloat()
    if (bitmapRatio > 1) {
        width = maxSize
        height = (width / bitmapRatio).toInt()
    } else {
        height = maxSize
        width = (height * bitmapRatio).toInt()
    }
    return Bitmap.createScaledBitmap(this, width, height, true)
}

fun Bitmap.convertToBase64(): String? {
    val outputStream = ByteArrayOutputStream()
    this.getResizedBitmap(100)?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
}
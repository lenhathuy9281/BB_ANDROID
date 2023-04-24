package com.social.bluebirdsocial.ui.custom

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat

fun Uri?.encodeImage(context: Context): String {
    if (this == null) return ""
    // Load the image from a file or other source
    val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, this)

// Convert the bitmap to a byte array
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    val byteArray = stream.toByteArray()

// Convert the byte array to a Base64 encoded string
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun String.decodeImage(): Bitmap? {
    // Convert the Base64 string to a Bitmap object
    val byteArray = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun Long.toDateString(): String? {
//    val dateAsString: String = java.lang.String.valueOf(longDate)
//    val date: Date = SimpleDateFormat("yyyyMMddHHmmss").parse(dateAsString)

    val sdf = SimpleDateFormat("dd/MM/yyyy")
    return sdf.format(this)
}
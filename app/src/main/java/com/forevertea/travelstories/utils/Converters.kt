package com.forevertea.travelstories.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.room.TypeConverter
import com.forevertea.travelstories.data.album.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type
import java.util.*

class UriConverters {
    @TypeConverter
    fun fromString(value: String?): Uri? {
        return if (value == null) null else Uri.parse(value)
    }

    @TypeConverter
    fun toString(uri: Uri?): String? {
        return uri?.toString()
    }
}

class ListConverters {

    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): List<String> {
        if (!value.isNullOrEmpty() && value != "[{}]") {
            return Gson().fromJson(value, Array<String>::class.java).toList()
        } else {
            return emptyList()
        }
    }
}

class BitMapConverter {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}

class ImageListConverter{
    @TypeConverter
    fun fromImageList(imagelist: List<Image?>?): String? {
        if (imagelist == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Image?>?>() {}.getType()
        return gson.toJson(imagelist, type)
    }

    @TypeConverter
    fun toImageList(imageListString: String?): List<Image>? {
        if (imageListString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Image?>?>() {}.getType()
        return gson.fromJson<List<Image>>(imageListString, type)
    }
}



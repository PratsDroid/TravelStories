package com.forevertea.travelstories.data.album

import android.graphics.Color
import androidx.compose.runtime.Immutable
import androidx.room.*
import com.forevertea.travelstories.utils.ImageListConverter
import com.forevertea.travelstories.utils.ListConverters

sealed class SealedAlbum {

    @PrimaryKey(autoGenerate = true)
    open var id: Long = 0

    @Entity(tableName = "photo")
    @Immutable
    data class Album(
        @PrimaryKey
        @ColumnInfo(name = "id") override var id: Long = 0,
        @ColumnInfo(name = "name") val name: String,
        @TypeConverters(ListConverters::class)
        @ColumnInfo(name = "uri_list") var uriList: List<String>? = null,
        @TypeConverters(ImageListConverter::class)
        @ColumnInfo(name = "images")
        var imageList : List<Image>
    ): SealedAlbum()

    @Entity(tableName = "Text")
    data class Text(
        @PrimaryKey
        @ColumnInfo(name = "id") override var id: Long = 0,
        val text: String,
        val backgroundColor: Color
    ): SealedAlbum()

}
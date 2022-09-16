package com.forevertea.travelstories.data.album


import androidx.room.ColumnInfo


data class Image(
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "uri") val uri: String,
)
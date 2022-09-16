package com.forevertea.travelstories.data.album

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlbumDao {

    @Insert
    fun insertAlbum(album: SealedAlbum.Album) : Long

    @Query("SELECT * FROM photo")
    fun getAllAlbums(): LiveData<List<SealedAlbum.Album>>

    @Query("SELECT * FROM photo where id = :id")
    fun getAlbum(id:Long): List<SealedAlbum.Album>
}
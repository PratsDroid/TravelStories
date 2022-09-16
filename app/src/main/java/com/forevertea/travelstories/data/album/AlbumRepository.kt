package com.forevertea.travelstories.data.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumRepository(private val albumDao : AlbumDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allAlbums: LiveData<List<SealedAlbum.Album>> = albumDao.getAllAlbums()

    fun getAlbum(id: Long): LiveData<List<SealedAlbum.Album>> {
        val liveData = MutableLiveData<List<SealedAlbum.Album>>()
        coroutineScope.launch(Dispatchers.IO) {
            liveData.postValue(albumDao.getAlbum(id))
        }
        return liveData
    }

    fun insertAlbum(album: SealedAlbum.Album): LiveData<Long>{
        val liveData = MutableLiveData<Long>()
        coroutineScope.launch(Dispatchers.IO) {
            liveData.postValue(albumDao.insertAlbum(album))
        }
        return liveData
    }

}
package com.forevertea.travelstories.viewmodels

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.forevertea.travelstories.data.TravelAlbumDatabase
import com.forevertea.travelstories.data.album.AlbumRepository
import com.forevertea.travelstories.data.album.SealedAlbum
import com.forevertea.travelstories.ui.reorder.ItemPosition

open class TravelActivityModel(application: Application) : AndroidViewModel(application) {

    val allAlbums: LiveData<List<SealedAlbum.Album>>
    private val repository: AlbumRepository
    val newAlbum: MutableState<SealedAlbum.Album> = mutableStateOf(
        SealedAlbum.Album(
            id = 0,
            name = "",
            uriList = listOf(),
            imageList = listOf()
        )
    )
    var onUpdate = mutableStateOf(0)
    init {
        val travelDb = TravelAlbumDatabase.getDatabase(application)
        val albumDao = travelDb.albumsDao()
        repository = AlbumRepository(albumDao)
        allAlbums = repository.allAlbums
    }

    private fun updateUI() {
        onUpdate.value = (0..1_000_000).random()
    }

    fun moveImage(from: ItemPosition, to: ItemPosition) {
        if (newAlbum.value != null) {
            newAlbum.value.imageList = newAlbum.value.imageList?.toMutableList()?.apply {
                add(to.index, removeAt(from.index))
                updateUI()
            }
        }
    }

    fun setNewAlbum(album: SealedAlbum.Album){
        newAlbum.value = album
    }

    fun insertAlbum(){
        newAlbum.value?.let { insertAlbum(it) }
    }

    private fun insertAlbum(album: SealedAlbum.Album): LiveData<Long>{
        return repository.insertAlbum(album = album)
    }

    fun getAlbum(id:Long) : LiveData<List<SealedAlbum.Album>>{
        return repository.getAlbum(id)
    }
}
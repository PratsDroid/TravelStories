package com.forevertea.travelstories.data

import android.content.Context
import androidx.room.*
import com.forevertea.travelstories.R
import com.forevertea.travelstories.data.album.AlbumDao
import com.forevertea.travelstories.data.album.SealedAlbum
import com.forevertea.travelstories.utils.ImageListConverter
import com.forevertea.travelstories.utils.ListConverters
import com.forevertea.travelstories.utils.UriConverters


@Database(entities = [SealedAlbum.Album::class,SealedAlbum.Text::class], version = 1)
@TypeConverters(UriConverters::class,ListConverters::class, ImageListConverter::class)
abstract class TravelAlbumDatabase: RoomDatabase() {

    abstract fun albumsDao(): AlbumDao


    companion object {
        @Volatile
        private var INSTANCE: TravelAlbumDatabase? = null
             fun getDatabase(context: Context): TravelAlbumDatabase {
                 val tempInstance = INSTANCE
                 if (tempInstance != null) {
                     return tempInstance
                 }
                 synchronized(this) {
                     val instance = Room.databaseBuilder(
                         context.applicationContext,
                         TravelAlbumDatabase::class.java, context.resources.getString(R.string.db_name)
                     )
                         .build()
                     INSTANCE = instance
                     return instance
                 }
             }
    }

}
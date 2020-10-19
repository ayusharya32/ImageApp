package com.easycodingg.imagesearchapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.easycodingg.imagesearchapp.data.converters.Converters
import com.easycodingg.imagesearchapp.model.photoobject.UnsplashPhoto

@Database(
    entities = [UnsplashPhoto::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class UnsplashDatabase: RoomDatabase(){

    abstract fun getUnsplashDao(): UnsplashDao
}
package com.easycodingg.imagesearchapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.easycodingg.imagesearchapp.model.photoobject.UnsplashPhoto

@Dao
interface UnsplashDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: UnsplashPhoto)

    @Delete
    suspend fun deletePhoto(photo: UnsplashPhoto)

    @Query("SELECT * FROM photos")
    fun getAllPhotos(): LiveData<List<UnsplashPhoto>>
}
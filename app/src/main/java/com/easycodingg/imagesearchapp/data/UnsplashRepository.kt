package com.easycodingg.imagesearchapp.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.easycodingg.imagesearchapp.api.UnsplashApi
import com.easycodingg.imagesearchapp.model.photoobject.UnsplashPhoto
import com.easycodingg.imagesearchapp.utilities.Utilities
import com.easycodingg.imagesearchapp.utilities.Utilities.DISCOVER_MAX_SIZE
import com.easycodingg.imagesearchapp.utilities.Utilities.DISCOVER_PAGE_SIZE
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashDao: UnsplashDao
) {
    fun getDiscoverResults(): LiveData<PagingData<UnsplashPhoto>>{
        val pageConfig = PagingConfig(
            pageSize = DISCOVER_PAGE_SIZE,
            maxSize = DISCOVER_MAX_SIZE,
            enablePlaceholders = false
        )

        return Pager(
            config = pageConfig,
            pagingSourceFactory = {UnsplashDiscoverPagingSource(unsplashApi)}
        ).liveData
    }

    fun getSearchResults(query: String): LiveData<PagingData<UnsplashPhoto>>{
        val pageConfig = PagingConfig(
            pageSize = DISCOVER_PAGE_SIZE,
            maxSize = DISCOVER_MAX_SIZE,
            enablePlaceholders = false
        )

        return Pager(
            config = pageConfig,
            pagingSourceFactory = {UnsplashSearchPagingSource(unsplashApi, query)}
        ).liveData
    }

    suspend fun insertPhoto(photo: UnsplashPhoto) = unsplashDao.insertPhoto(photo)

    suspend fun deletePhoto(photo: UnsplashPhoto) = unsplashDao.deletePhoto(photo)

    fun getAllPhotos() = unsplashDao.getAllPhotos()
}
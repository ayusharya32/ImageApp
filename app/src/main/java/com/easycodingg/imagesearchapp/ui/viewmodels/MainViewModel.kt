package com.easycodingg.imagesearchapp.ui.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.easycodingg.imagesearchapp.data.UnsplashRepository
import com.easycodingg.imagesearchapp.model.UnsplashSearchResponse
import com.easycodingg.imagesearchapp.model.photoobject.UnsplashPhoto
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Singleton
class MainViewModel @ViewModelInject constructor(
    private val repository: UnsplashRepository
): ViewModel() {

    companion object{
        private const val DEFAULT_QUERY = ""
    }
    
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val discoverImages = getDiscoverPhotos()

    val searchImages = currentQuery.switchMap {
        repository.getSearchResults(it).cachedIn(viewModelScope)
    }

    private fun getDiscoverPhotos() =
        repository.getDiscoverResults().cachedIn(viewModelScope)

    fun getSearchImages(query: String) {
        currentQuery.value = query
    }

    fun insertPhoto(photo: UnsplashPhoto) = viewModelScope.launch {
        repository.insertPhoto(photo)
    }

    fun deletePhoto(photo: UnsplashPhoto) = viewModelScope.launch {
        repository.deletePhoto(photo)
    }

    fun getAllPhotos() = repository.getAllPhotos()
}
package com.easycodingg.imagesearchapp.model

import com.easycodingg.imagesearchapp.model.photoobject.UnsplashPhoto

data class UnsplashSearchResponse(
    val results: List<UnsplashPhoto>,
    val total: Int,
    val total_pages: Int
)
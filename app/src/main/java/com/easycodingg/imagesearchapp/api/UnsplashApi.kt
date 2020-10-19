package com.easycodingg.imagesearchapp.api

import com.easycodingg.imagesearchapp.model.UnsplashDiscoverResponse
import com.easycodingg.imagesearchapp.model.UnsplashSearchResponse
import com.easycodingg.imagesearchapp.utilities.Utilities.CLIENT_ID
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("photos")
    suspend fun discoverPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): UnsplashDiscoverResponse

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashSearchResponse
}
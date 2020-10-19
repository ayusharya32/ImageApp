package com.easycodingg.imagesearchapp.data

import androidx.paging.PagingSource
import com.easycodingg.imagesearchapp.api.UnsplashApi
import com.easycodingg.imagesearchapp.model.photoobject.UnsplashPhoto
import com.easycodingg.imagesearchapp.utilities.Utilities
import retrofit2.HttpException
import retrofit2.http.Query
import java.io.IOException

class UnsplashSearchPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String
): PagingSource<Int, UnsplashPhoto>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?:  Utilities.STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.searchPhotos(query, position ,params.loadSize)
            val photos = response.results

            LoadResult.Page(
                photos,
                if (position == Utilities.STARTING_PAGE_INDEX) null else position - 1,
                if (photos.isEmpty()) null else position + 1
            )
        } catch(e: IOException) {
            LoadResult.Error(e)
        } catch(e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
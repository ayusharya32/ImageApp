package com.easycodingg.imagesearchapp.data

import androidx.paging.PagingSource
import com.easycodingg.imagesearchapp.api.UnsplashApi
import com.easycodingg.imagesearchapp.model.photoobject.UnsplashPhoto
import com.easycodingg.imagesearchapp.utilities.Utilities
import com.easycodingg.imagesearchapp.utilities.Utilities.ORDER_BY_TYPE_LATEST
import retrofit2.HttpException
import java.io.IOException

class UnsplashDiscoverPagingSource(
    private val unsplashApi: UnsplashApi
): PagingSource<Int, UnsplashPhoto>(){

    companion object{
        var orderByType = ORDER_BY_TYPE_LATEST
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?:  Utilities.STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.discoverPhotos(position, params.loadSize, orderByType)

            LoadResult.Page(
                response,
                if (position == Utilities.STARTING_PAGE_INDEX) null else position - 1,
                if (response.isEmpty()) null else position + 1
            )
        } catch(e: IOException) {
            LoadResult.Error(e)
        } catch(e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
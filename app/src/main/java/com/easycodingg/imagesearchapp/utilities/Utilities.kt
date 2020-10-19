package com.easycodingg.imagesearchapp.utilities

import com.easycodingg.imagesearchapp.BuildConfig

object Utilities {

    const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY
    const val BASE_URL = "https://api.unsplash.com/"

    const val STARTING_PAGE_INDEX = 1
    const val DISCOVER_PAGE_SIZE = 20
    const val DISCOVER_MAX_SIZE = 100

    const val ORDER_BY_TYPE_LATEST = "latest"
    const val ORDER_BY_TYPE_POPULAR = "popular"
    const val ORDER_BY_TYPE_OLDEST = "oldest"

    const val UNSPLASH_DATABASE_NAME = "unsplash_db"
}
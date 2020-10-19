package com.easycodingg.imagesearchapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.easycodingg.imagesearchapp.api.UnsplashApi
import com.easycodingg.imagesearchapp.data.UnsplashDatabase
import com.easycodingg.imagesearchapp.utilities.Utilities.BASE_URL
import com.easycodingg.imagesearchapp.utilities.Utilities.UNSPLASH_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit() :Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideUnsplashApi(retrofit: Retrofit): UnsplashApi =
        retrofit.create(UnsplashApi::class.java)

    @Singleton
    @Provides
    fun provideUnsplashDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(app, UnsplashDatabase::class.java, UNSPLASH_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideUnsplashDao(db: UnsplashDatabase) = db.getUnsplashDao()
}
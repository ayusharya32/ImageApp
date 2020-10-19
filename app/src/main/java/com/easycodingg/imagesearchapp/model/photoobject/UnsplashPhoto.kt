package com.easycodingg.imagesearchapp.model.photoobject

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "photos"
)
data class UnsplashPhoto(
    val description: String?,
    @PrimaryKey
    val id: String,
    val links: Links,
    val urls: Urls,
    val user: User
): Serializable
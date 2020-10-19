package com.easycodingg.imagesearchapp.model.photoobject

import java.io.Serializable

data class User(
    val id: String,
    val links: LinksUser,
    val location: String,
    val name: String,
    val profile_image: ProfileImage,
    val username: String
): Serializable
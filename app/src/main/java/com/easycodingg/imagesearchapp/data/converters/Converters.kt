package com.easycodingg.imagesearchapp.data.converters

import androidx.room.TypeConverter
import com.easycodingg.imagesearchapp.model.photoobject.Links
import com.easycodingg.imagesearchapp.model.photoobject.LinksUser
import com.easycodingg.imagesearchapp.model.photoobject.Urls
import com.easycodingg.imagesearchapp.model.photoobject.User
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromLinksToJson(links: Links): String{
        return Gson().toJson(links)
    }

    @TypeConverter
    fun fromJsonToLinks(value: String): Links{
        return Gson().fromJson(value, Links::class.java)
    }

    @TypeConverter
    fun fromUrlsToJson(urls: Urls): String{
        return Gson().toJson(urls)
    }

    @TypeConverter
    fun fromJsonToUrls(value: String): Urls{
        return Gson().fromJson(value, Urls::class.java)
    }

    @TypeConverter
    fun fromUserToJson(user: User): String{
        return Gson().toJson(user)
    }

    @TypeConverter
    fun fromJsonToUser(value: String): User{
        return Gson().fromJson(value, User::class.java)
    }
}
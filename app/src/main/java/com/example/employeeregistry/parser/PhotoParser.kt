package com.example.employeeregistry.parser

import com.example.employeeregistry.model.Photo
import org.json.JSONArray

class PhotoParser (val photoResponse: String?) {
    fun parseAlbumResponse() : ArrayList<Photo> {
        var photoList: ArrayList<Photo> = ArrayList()
        val jsonArray = JSONArray(photoResponse)
        for(i in 0 until jsonArray.length()) {
            val photoJsonObject = jsonArray.optJSONObject(i)
            val albumId = photoJsonObject.optInt("albumId")
            val id = photoJsonObject.optInt("id")
            val title = photoJsonObject.optString("title")
            val url = photoJsonObject.optString("url")
            val thumbnailUrl = photoJsonObject.optString("thumbnailUrl")
            photoList.add(Photo(albumId, id, title, url, thumbnailUrl))
        }
        return photoList
    }
}
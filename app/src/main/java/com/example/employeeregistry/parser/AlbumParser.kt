package com.example.employeeregistry.parser

import com.example.employeeregistry.model.Album
import org.json.JSONArray

class AlbumParser (private val albumResponse: String?) {
    fun parseAlbumResponse() : ArrayList<Album> {
        var albumList: ArrayList<Album> = ArrayList()
        val jsonArray = JSONArray(albumResponse)
        for(i in 0 until jsonArray.length()) {
            val albumJsonObject = jsonArray.optJSONObject(i)
            val userId = albumJsonObject.optInt("userId")
            val id = albumJsonObject.optInt("id")
            val title = albumJsonObject.optString("title")
            albumList.add(Album(userId, id, title))
        }
        return albumList
    }
}
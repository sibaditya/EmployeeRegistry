package com.example.news.repository

import android.content.Context
import android.net.ConnectivityManager

object URLUtils {
    /**
     * Url to hit to fetch the YELP SEARCH
     */
    const val BASE_URL = "https://engineering.league.dev/challenge/api"
    const val LOGIN = "/login"
    const val USERS = "/users"
    const val POSTS = "/posts?userId="
    const val ALBUMS = "/albums?userId="
    const val PHOTOS = "/photos?albumId="

    /**
     * Method to check the availability of internet
     *
     * @param context [Context]
     * @return boolean
     */
    fun isInternetAvailable(context: Context?): Boolean {
        val cm =
            (context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun getURL(urlType: String, param: String): String {
        var url = ""
        when(urlType) {
            LOGIN -> url = BASE_URL + LOGIN
            USERS -> url = BASE_URL + USERS  + param
            POSTS -> url = BASE_URL + POSTS  + param
            ALBUMS -> url = BASE_URL + ALBUMS + param
            PHOTOS -> url = BASE_URL + PHOTOS + param
        }
        return url
    }
}
package com.example.news.repository

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.employeeregistry.R
import com.example.employeeregistry.application.AppController
import com.example.news.repository.NetworkUtils.FetchNetworkResponse
import java.util.*

/**
 * Utility class for Rss Feeds
 */
class NetworkUtils(
    /**
     * [FetchNetworkResponse] callback listener object
     */
    private val dataResponse: FetchNetworkResponse,
    /**
     * Context
     */
    private val mContext: Context?
) {
    /**
     * TAG for logs
     */
    private val TAG = javaClass.simpleName
    /**
     * Array list to store the titles of the read RSS feeds
     */
    private val readItemsList: ArrayList<String>? = null

    /**
     * Method to fetch feed from the network
     */
    fun fetchFeedFromNetwork(url: String, headers: HashMap<String, String?>) {
        Log.d(TAG, "fetchFeedFromNetwork")
        Log.d(TAG, "url = $url")
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(mContext)
        // Request a string response from the provided URL.
        val stringRequest = object: StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                Log.d(TAG, "on Success")
                handleResponse(response)
            },
            Response.ErrorListener { dataResponse.onFailure(mContext?.getString(R.string.something_went_wrong)) })
        {
            override fun getHeaders(): MutableMap<String, String?> {
                return headers
            }
        }
        // Add the request to the RequestQueue.
        Log.d("StringRequest", stringRequest.url)
        AppController.getInstance()?.addToRequestQueue(stringRequest)
    }

    /**
     * Method to handle the response data either fetched from network or cache
     *
     * @param response String
     */
    fun handleResponse(response: String?) {
        Log.d(TAG, "handle response")
        dataResponse.onSuccess(response)
    }

    /**
     * Interface for callback to [ResultListActivity]
     */
    interface FetchNetworkResponse {
        fun onSuccess(msg: String?)
        fun onFailure(msg: String?)
    }

    companion object {
        /**
         * Key to store  read feeds to Shared preference
         */
        private const val SHARED_PREF_RSS_FEED_READ_KEY = "rss_read_feed"
        /**
         * Key to store next date midnight time to Shared preference
         */
        private const val SHARED_PREF_TIME_KEY = "saved_time"
    }

}
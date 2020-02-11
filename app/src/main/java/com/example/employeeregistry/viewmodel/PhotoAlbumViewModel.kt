package com.example.employeeregistry.viewmodel

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.employeeregistry.R
import com.example.employeeregistry.model.Photo
import com.example.employeeregistry.parser.AlbumParser
import com.example.employeeregistry.parser.PhotoParser
import com.example.employeeregistry.util.SharedPreferenceUtil
import com.example.news.repository.NetworkUtils
import com.example.news.repository.URLUtils

class PhotoAlbumViewModel : ViewModel(), NetworkUtils.FetchNetworkResponse {
    lateinit var photoLiveData: MutableLiveData<ArrayList<Photo>>
    lateinit var photoArrayList: ArrayList<Photo>
    lateinit var albumParser: AlbumParser
    lateinit var photoParser: PhotoParser
    lateinit var networkUtils: NetworkUtils
    lateinit var activityContext: Context
    lateinit var networkFailureListener: NetworkFailureListener
    var albumId = ""
    interface NetworkFailureListener{
        fun onNetworkFailure(error: String?)
    }

    fun getPhotoMutableLiveData(): MutableLiveData<ArrayList<Photo>> {
        return photoLiveData
    }

    fun init(context: Context, failureListener: NetworkFailureListener, userId: String) {
        activityContext = context
        networkFailureListener = failureListener
        photoLiveData =
            MutableLiveData()
        networkUtils = NetworkUtils(this, context)
        if(URLUtils.isInternetAvailable(context)) {
            val apiKey: String? = SharedPreferenceUtil.read(SharedPreferenceUtil.API_KEY, SharedPreferenceUtil.API_KEY_DEFAULT)
            val headers = HashMap<String, String?>()
            headers["x-access-token"] = apiKey
            networkUtils.fetchFeedFromNetwork(URLUtils.getURL(URLUtils.ALBUMS, userId), headers)
        } else {
            networkFailureListener.onNetworkFailure(context?.getString(R.string.internet_not_available))
        }
    }

    private fun populateList(msg: String?): ArrayList<Photo>? {
        photoParser = PhotoParser(msg)
        photoArrayList = photoParser.parseAlbumResponse()
        return photoArrayList
    }

    override fun onSuccess(msg: String?) {
        if(albumId.isNullOrEmpty()) {
            albumParser = AlbumParser(msg)
            albumId = albumParser?.parseAlbumResponse()[0]?.id?.toString()
            callPhotoApi()
        } else {
            photoLiveData?.postValue(populateList(msg))
        }
    }

    fun callPhotoApi() {
        if(URLUtils.isInternetAvailable(activityContext)) {
            val apiKey: String? = SharedPreferenceUtil.read(SharedPreferenceUtil.API_KEY, SharedPreferenceUtil.API_KEY_DEFAULT)
            val headers = HashMap<String, String?>()
            headers["x-access-token"] = apiKey
            networkUtils.fetchFeedFromNetwork(URLUtils.getURL(URLUtils.PHOTOS, albumId), headers)
        } else {
            networkFailureListener.onNetworkFailure(activityContext?.getString(R.string.internet_not_available))
        }
    }

    override fun onFailure(msg: String?) {
        networkFailureListener.onNetworkFailure(activityContext?.getString(R.string.something_went_wrong))
    }
}
package com.example.employeeregistry.viewmodel

import android.content.Context
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.employeeregistry.R
import com.example.employeeregistry.parser.LoginParser
import com.example.news.repository.NetworkUtils
import com.example.news.repository.URLUtils

class SplashScreenViewModel : ViewModel(), NetworkUtils.FetchNetworkResponse {

    interface NetworkFailureListener{
        fun onNetworkFailure(error: String?)
    }

    lateinit var loginLiveData: MutableLiveData<String>
    lateinit var networkFailureListener: NetworkFailureListener
    lateinit var context: Context

    fun init(c: Context, failureListener: NetworkFailureListener) {
        context = c
        networkFailureListener = failureListener
        loginLiveData = MutableLiveData<String>()
    }

    fun getAPIKey(username: String, password: String) {
        val networkUtils = NetworkUtils(this, context)
        if(URLUtils.isInternetAvailable(context)) {
            val credentials = "$username:$password"
            val auth = "Basic " + Base64.encodeToString(credentials.toByteArray(),
                Base64.NO_WRAP)
            val headers = HashMap<String, String?>()
            headers["Authorization"] = auth
            networkUtils.fetchFeedFromNetwork(URLUtils.getURL(URLUtils.LOGIN, ""), headers)
        } else {
            networkFailureListener.onNetworkFailure(context.getString(R.string.internet_not_available))
        }
    }

    override fun onSuccess(msg: String?) {
        val loginParse = LoginParser(msg)
        loginLiveData.postValue(loginParse.parseLoginResponse())
    }

    override fun onFailure(msg: String?) {
        networkFailureListener.onNetworkFailure(context.getString(R.string.something_went_wrong))
    }

}
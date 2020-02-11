package com.example.employeeregistry.viewmodel

import Users
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.employeeregistry.R
import com.example.employeeregistry.parser.UserParser
import com.example.employeeregistry.util.SharedPreferenceUtil
import com.example.news.repository.NetworkUtils
import com.example.news.repository.URLUtils
import java.util.ArrayList

class MainActivityViewModel : ViewModel(),NetworkUtils.FetchNetworkResponse {
    lateinit var usersLiveData: MutableLiveData<ArrayList<Users>>
    lateinit var usersArrayList: ArrayList<Users>
    lateinit var usersParser: UserParser
    lateinit var networkUtils: NetworkUtils
    lateinit var activityContext: Context
    lateinit var networkFailureListener: NetworkFailureListener
    interface NetworkFailureListener{
        fun onNetworkFailure(error: String?)
    }

    fun getUserMutableLiveData(): MutableLiveData<ArrayList<Users>> {
        return usersLiveData
    }

    fun init(context: Context, failureListener: NetworkFailureListener) {
        activityContext = context
        networkFailureListener = failureListener
        usersLiveData =
            MutableLiveData()
        networkUtils = NetworkUtils(this, context)
        if(URLUtils.isInternetAvailable(context)) {
            val apiKey: String? = SharedPreferenceUtil.read(SharedPreferenceUtil.API_KEY, SharedPreferenceUtil.API_KEY_DEFAULT)
            val headers = HashMap<String, String?>()
            headers["x-access-token"] = apiKey
            networkUtils.fetchFeedFromNetwork(URLUtils.getURL(URLUtils.USERS,""), headers)
        } else {
            networkFailureListener.onNetworkFailure(context.getString(R.string.internet_not_available))
        }
    }

    private fun populateList(msg: String?): ArrayList<Users>? {
        usersParser = UserParser(msg)
        usersArrayList = usersParser.parseUserResponse()
        return usersArrayList
    }

    override fun onSuccess(msg: String?) {
        usersLiveData?.postValue(populateList(msg))
    }

    override fun onFailure(msg: String?) {
        networkFailureListener.onNetworkFailure(activityContext.getString(R.string.something_went_wrong))
    }
}
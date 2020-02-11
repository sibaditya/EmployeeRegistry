package com.example.employeeregistry.application

import android.app.Application
import android.text.TextUtils
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class AppController : Application() {
    private var mRequestQueue: RequestQueue? = null

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        instance = this
    }

    val requestQueue: RequestQueue?
        get() {
            Log.d(TAG, "getRequestQueue")
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(applicationContext)
            }
            return mRequestQueue
        }

    fun <T> addToRequestQueue(
        req: Request<T>,
        tag: String?
    ) {
        Log.d(TAG, "addToRequestQueue")
        req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        requestQueue!!.add(req)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        Log.d(TAG, "addToRequestQueue")
        req.tag = TAG
        requestQueue!!.add(req)
    }

    fun cancelPendingRequests(tag: Any?) {
        Log.d(TAG, "cancelPendingRequests")
        if (mRequestQueue != null) {
            mRequestQueue!!.cancelAll(tag)
        }
    }



    companion object {
        val TAG = AppController::class.java.simpleName
        private var instance: AppController? = null

        @Synchronized
        fun getInstance(): AppController? {
            return instance
        }
    }
}
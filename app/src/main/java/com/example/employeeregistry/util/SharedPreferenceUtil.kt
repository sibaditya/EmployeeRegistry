package com.example.employeeregistry.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences


object SharedPreferenceUtil {

    const val API_KEY = "API_KEY"
    const val API_KEY_DEFAULT = "KEY_NOT_FOUND"
    lateinit var sharedPref: SharedPreferences

    fun init(context: Context) {
            sharedPref = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    fun read(key: String?, defValue: String?): String? {
        return sharedPref?.getString(key, defValue)
    }

    fun write(key: String?, value: String?) {
        val prefsEditor: SharedPreferences.Editor = sharedPref.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }

}
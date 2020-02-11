package com.example.employeeregistry.parser

import org.json.JSONObject

class LoginParser(val loginResponse: String?) {

    fun parseLoginResponse() : String {
        val jsonObject = JSONObject(loginResponse)
        return jsonObject.optString("api_key")
    }

}
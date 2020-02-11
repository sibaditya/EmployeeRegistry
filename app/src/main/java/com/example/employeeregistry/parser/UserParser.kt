package com.example.employeeregistry.parser

import Address
import Avatar
import Company
import Geo
import Users
import org.json.JSONArray

class UserParser(val userResponse: String?) {

    fun parseUserResponse() : ArrayList<Users> {
        var userList: ArrayList<Users> = ArrayList()
        val jsonArray = JSONArray(userResponse)
        for(i in 0 until jsonArray.length()) {
            val userJsonObject = jsonArray.optJSONObject(i)
            val id = userJsonObject.optInt("id")

            //Parse avatar
            val avatarJsonObject = userJsonObject.optJSONObject("avatar")
            val largePic = avatarJsonObject.optString("large")
            val mediumPic = avatarJsonObject.optString("medium")
            val thumbnail = avatarJsonObject.optString("thumbnail")
            val avatar = Avatar(largePic, mediumPic, thumbnail)

            val name = userJsonObject.optString("name")
            val username = userJsonObject.optString("username")
            val email = userJsonObject.optString("email")

            //parse address
            val addressJsonObject = userJsonObject.optJSONObject("address")
            val street = addressJsonObject.optString("street")
            val suite = addressJsonObject.optString("suite")
            val city = addressJsonObject.optString("city")
            val zipcode = addressJsonObject.optString("zipcode")
            val geoJsonObject = addressJsonObject.getJSONObject("geo")
            val geo = Geo(geoJsonObject.optDouble("lat"), geoJsonObject.optDouble("lng"))
            val address = Address(street, suite, city, zipcode, geo)

            val phone = userJsonObject.optString("phone")
            val website = userJsonObject.optString("website")

            //parse Company
            val companyJsonObject = userJsonObject.optJSONObject("company")
            val company = Company(companyJsonObject.optString("name"),
                                    companyJsonObject.optString("catchPhrase"),
                                    companyJsonObject.optString("bs"))
            val user = Users(id, avatar, name, username, email, address, phone, website, company)
            userList.add(user)
        }

        return userList

    }
}
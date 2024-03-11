package com.project.centennial.securecaremobileapp.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.centennial.securecaremobileapp.model.DataResponse

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUserInfo(response: DataResponse) {
        val jsonString = gson.toJson(response.data)
        Log.d("Shared Preferences-save: ", jsonString.toString())
        sharedPreferences.edit()
            .putString("userInfo", jsonString)
            .putString("userToken", response.token)
            .apply()
    }

    fun getUserInfo(): Map<String, Any> {
        val jsonString = sharedPreferences.getString("userInfo", "{}") ?: "{}"
        val type = object : TypeToken<Map<String, Any>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    fun getUserInfoString(): String? {
        return sharedPreferences.getString("userInfo", "")
    }
    fun getUserToken(): String? {
        return sharedPreferences.getString("userToken", "")

    }

    fun clearUserLogin() {
        sharedPreferences.edit().remove("userToken").apply()
        sharedPreferences.edit().remove("userInfo").apply()
    }
}
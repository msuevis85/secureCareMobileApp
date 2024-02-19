package com.project.centennial.securecaremobileapp.utils

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.project.centennial.securecaremobileapp.model.User
import com.project.centennial.securecaremobileapp.model.UserResponse

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUserInfo(data: UserResponse) {
        val jsonString = gson.toJson(data.user)
        sharedPreferences.edit()
            .putString("userInfo", jsonString)
            .putString("userToken", data.token)
            .apply()


    }

    fun getUserInfo(): User? {
        val jsonString = sharedPreferences.getString("userInfo", null)
        return gson.fromJson(jsonString, User::class.java)
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
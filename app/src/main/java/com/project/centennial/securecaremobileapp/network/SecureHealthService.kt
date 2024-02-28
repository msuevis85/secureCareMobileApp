package com.project.centennial.securecaremobileapp.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SecureHealthService {
    var gson = GsonBuilder()
        .setLenient()
        .create()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://telehealthapi.nguyentranvn.com")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val userService: UserApi = retrofit.create(UserApi::class.java)
    val consultationService: ConsultationApi = retrofit.create(ConsultationApi::class.java)
}

// https://telehealthapi.nguyentranvn.com
// http://10.0.2.2:4000
package com.project.centennial.securecaremobileapp.network

import com.project.centennial.securecaremobileapp.model.User
import com.project.centennial.securecaremobileapp.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @POST("/api/user/login")
    @JvmSuppressWildcards
    suspend fun login(@Body body: Map<String, Any>) : UserResponse

    @POST("/api/user/register")
    @JvmSuppressWildcards
    suspend fun register(@Body body: Map<String, Any>) : UserResponse

    @POST("/api/user/updateprofile")
    @JvmSuppressWildcards
    suspend fun updateProfile(
        @Header("x-auth-token") token: String,
        @Body body: Map<String, Any>
    ) : UserResponse


    @GET("/api/user/profile/{userid}/{usertypeid}")
    @JvmSuppressWildcards
    suspend fun getProfile(
        @Header("x-auth-token") token: String,
        @Path("userid") userid: String,
        @Path("usertypeid") usertypeid: Int,
    ) : UserResponse
}
// eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiJlZmE4N2Q2Ni1jMjg0LWJmZjQtNjM2YS0yYjdhMDdlMmUzMmIiLCJpYXQiOjE3MDc4Nzk0MTF9.DmdMcCLcFDdjMyo-MVfg7v6B7qgc2n83NEIelNCLvJw
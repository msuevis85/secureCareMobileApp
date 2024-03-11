package com.project.centennial.securecaremobileapp.network

import com.project.centennial.securecaremobileapp.model.DataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("/api/user/login")
    @JvmSuppressWildcards
    suspend fun login(@Body body: Map<String, Any>) : DataResponse


    @GET("/api/user/profile/{userid}/{usertypeid}")
    @JvmSuppressWildcards
    suspend fun getProfile(
        @Header("x-auth-token") token: String,
        @Path("userid") userid: String,
        @Path("usertypeid") usertypeid: Int,
    ) : DataResponse

    //////////////////// new
    @POST("/api/user/addpatient")
    @JvmSuppressWildcards
    suspend fun addPatient(@Body body: Map<String, Any>) : DataResponse

    @POST("/api/user/addspecialist")
    @JvmSuppressWildcards
    suspend fun addSpecialist(@Body body: Map<String, Any>) : DataResponse

    @POST("/api/user/updatepatient")
    @JvmSuppressWildcards
    suspend fun updatePatient(
        @Header("x-auth-token") token: String,
        @Body body: Map<String, Any?>
    ) : DataResponse

    @POST("/api/user/updatespecialist")
    @JvmSuppressWildcards
    suspend fun updateSpecialist(
        @Header("x-auth-token") token: String,
        @Body body: Map<String, Any?>
    ) : DataResponse
}
// eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiJlZmE4N2Q2Ni1jMjg0LWJmZjQtNjM2YS0yYjdhMDdlMmUzMmIiLCJpYXQiOjE3MDc4Nzk0MTF9.DmdMcCLcFDdjMyo-MVfg7v6B7qgc2n83NEIelNCLvJw
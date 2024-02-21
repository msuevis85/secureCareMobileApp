package com.project.centennial.securecaremobileapp.network

import com.project.centennial.securecaremobileapp.model.DataArrayResponse
import com.project.centennial.securecaremobileapp.model.DataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ConsultationApi {
    @GET("/api/consultation/specialists")
    @JvmSuppressWildcards
    suspend fun getSpecialists(
        @Header("x-auth-token") token: String
    ) : DataArrayResponse


    @GET("/api/consultation/specialists/{search}")
    @JvmSuppressWildcards
    suspend fun searchSpecialists(
        @Path("search") search: String
    ) : DataArrayResponse

    @POST("/api/consultation/bookappointment")
    @JvmSuppressWildcards
    suspend fun bookAppointment(
        @Header("x-auth-token") token: String,
        @Body body: Map<String, String>
    ) : DataResponse

    @GET("/api/consultation/specialist/schedules/{specialistid}/{date}")
    @JvmSuppressWildcards
    suspend fun getSpecialistSchedules(
        @Header("x-auth-token") token: String,
        @Path("specialistid") specialistid: String,
        @Path("date") date: String,
    ) : DataArrayResponse
}
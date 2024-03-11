package com.project.centennial.securecaremobileapp.network

import com.project.centennial.securecaremobileapp.model.DataArrayResponse
import com.project.centennial.securecaremobileapp.model.DataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface SpecialistApi {

    @GET("/api/specialist/waitingappointments/{userid}")
    @JvmSuppressWildcards
    suspend fun getWaitingAppointments(
        @Header("x-auth-token") token: String,
        @Path("userid") userid: String,
    ) : DataArrayResponse

    @GET("/api/specialist/schedulebydate/{userid}/{date}")
    @JvmSuppressWildcards
    suspend fun getSpecialistScheduleByDate(
        @Header("x-auth-token") token: String,
        @Path("userid") userid: String,
        @Path("date") date: String,
    ) : DataArrayResponse

    @POST("/api/specialist/confirmappointment")
    @JvmSuppressWildcards
    suspend fun confirmAppointment(
        @Header("x-auth-token") token: String,
        @Body body: Map<String, Any?>
    ) : DataResponse
}
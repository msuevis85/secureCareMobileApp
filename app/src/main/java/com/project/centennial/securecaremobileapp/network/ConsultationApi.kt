package com.project.centennial.securecaremobileapp.network

import com.project.centennial.securecaremobileapp.model.DataArrayResponse
import com.project.centennial.securecaremobileapp.model.DataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ConsultationApi {
    @GET("/api/consultation/specialists/medical/{medicalspecialtyid}")
    @JvmSuppressWildcards
    suspend fun getSpecialists(
        @Path("medicalspecialtyid") medicalspecialtyid: Int,
        @Header("x-auth-token") token: String
    ) : DataArrayResponse

    @GET("/api/consultation/medicalspecialties")
    @JvmSuppressWildcards
    suspend fun getMedicalSpecialties() : DataArrayResponse


    @GET("/api/consultation/specialists/{search}")
    @JvmSuppressWildcards
    suspend fun searchSpecialists(
        @Path("search") search: String
    ) : DataArrayResponse

    @POST("/api/consultation/bookappointment")
    @JvmSuppressWildcards
    suspend fun bookAppointment(
        @Header("x-auth-token") token: String,
        @Body body: Map<String, Any?>
    ) : DataResponse

    @POST("/api/consultation/requestappointment")
    @JvmSuppressWildcards
    suspend fun requestAppointment(
        @Header("x-auth-token") token: String,
        @Body body: Map<String, Any?>
    ) : DataResponse

    @GET("/api/consultation/specialist/schedules/{specialistid}/{date}")
    @JvmSuppressWildcards
    suspend fun getSpecialistSchedules(
        @Header("x-auth-token") token: String,
        @Path("specialistid") specialistid: String,
        @Path("date") date: String,
    ) : DataArrayResponse

    @GET("/api/consultation/appointments/{userid}")
    @JvmSuppressWildcards
    suspend fun getAppointmentsByUserId(
        @Header("x-auth-token") token: String,
        @Path("userid") userid: String,
    ) : DataArrayResponse

    @GET("/api/consultation/cancelappointment/{appointmentid}")
    @JvmSuppressWildcards
    suspend fun cancelAppointment(
        @Header("x-auth-token") token: String,
        @Path("appointmentid") appointmentid: String,
    ) : DataResponse



    @POST("/api/consultation/updatesymptom")
    @JvmSuppressWildcards
    suspend fun updateSymptomAppointment(
        @Header("x-auth-token") token: String,
        @Body body: Map<String, Any?>
    ) : DataResponse

    @POST("/api/consultation/updateappointment")
    @JvmSuppressWildcards
    suspend fun updateAppointment(
        @Header("x-auth-token") token: String,
        @Body body: Map<String, Any?>
    ) : DataResponse



}
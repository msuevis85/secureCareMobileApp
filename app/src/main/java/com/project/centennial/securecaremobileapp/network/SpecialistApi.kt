package com.project.centennial.securecaremobileapp.network

import com.project.centennial.securecaremobileapp.model.DataArrayResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SpecialistApi {

    @GET("/api/specialist/waitingappointments/{userid}")
    @JvmSuppressWildcards
    suspend fun getWaitingAppointments(
        @Header("x-auth-token") token: String,
        @Path("userid") userid: String,
    ) : DataArrayResponse

}
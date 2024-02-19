package com.project.centennial.securecaremobileapp.repository

import android.util.Log
import com.project.centennial.securecaremobileapp.model.DataArrayResponse
import com.project.centennial.securecaremobileapp.model.DataResponse
import com.project.centennial.securecaremobileapp.model.UserResponse
import com.project.centennial.securecaremobileapp.network.SecureHealthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class ConsultationRepo {

    suspend fun getSpecialists(token: String) : DataArrayResponse? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("token: ", token)
                val service = SecureHealthService.consultationService
                val data = service.getSpecialists(token)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun getSpecialistSchedules(token: String,
                                       date: String,
                                       specialistid: String) : DataArrayResponse? {
        return withContext(Dispatchers.IO) {
            try {

                val service = SecureHealthService.consultationService
                val data = service.getSpecialistSchedules(token,specialistid, date)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun bookAppointment(token:String,body: Map<String, String>): DataResponse?{
        return withContext(Dispatchers.IO) {
            try {

                val service = SecureHealthService.consultationService
                val data = service.bookAppointment(token,body)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

}
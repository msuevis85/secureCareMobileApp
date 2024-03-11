package com.project.centennial.securecaremobileapp.repository

import android.util.Log
import com.project.centennial.securecaremobileapp.model.DataArrayResponse
import com.project.centennial.securecaremobileapp.model.DataResponse
import com.project.centennial.securecaremobileapp.network.SecureHealthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class SpecialistRepo {
    suspend fun getWaitingAppointments(token: String, userid: String) : DataArrayResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val service = SecureHealthService.specialistService
                val data = service.getWaitingAppointments(token, userid)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun getSchedulesByDate(token: String, userid: String, date: String) : DataArrayResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val service = SecureHealthService.specialistService
                val data = service.getSpecialistScheduleByDate(token, userid, date)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun confirmAppointment(token: String, body: Map<String,Any>) : DataResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val service = SecureHealthService.specialistService
                val data = service.confirmAppointment(token, body)
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
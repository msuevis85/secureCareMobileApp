package com.project.centennial.securecaremobileapp.repository

import android.util.Log
import com.project.centennial.securecaremobileapp.model.DataArrayResponse
import com.project.centennial.securecaremobileapp.model.DataResponse
import com.project.centennial.securecaremobileapp.network.SecureHealthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class ConsultationRepo {

    suspend fun getSpecialists(token: String, medicalspecialtyid: Int) : DataArrayResponse? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("token: ", token)
                val service = SecureHealthService.consultationService
                val data = service.getSpecialists(medicalspecialtyid, token)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun getMedicalSpecialties() : DataArrayResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val service = SecureHealthService.consultationService
                val data = service.getMedicalSpecialties()
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

    suspend fun bookAppointment(token:String, body: Map<String, Any?>): DataResponse?{
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

    suspend fun requestAppointment(token:String, body: Map<String, Any?>): DataResponse?{
        return withContext(Dispatchers.IO) {
            try {

                val service = SecureHealthService.consultationService
                val data = service.requestAppointment(token,body)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun searchSpecialists(search: String): DataArrayResponse? {
        return withContext(Dispatchers.IO) {
            try {

                val service = SecureHealthService.consultationService
                val data = service.searchSpecialists(search)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun getAppointmentsByUserId(token: String,userid: String): DataArrayResponse? {
        return withContext(Dispatchers.IO) {
            try {

                val service = SecureHealthService.consultationService
                val data = service.getAppointmentsByUserId(token,userid)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun cancelAppointment(token: String,appointmentid: String): DataResponse? {
        return withContext(Dispatchers.IO) {
            try {

                val service = SecureHealthService.consultationService
                val data = service.cancelAppointment(token,appointmentid)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun updateSymptom(token: String, body: Map<String, Any>): DataResponse? {
        return withContext(Dispatchers.IO) {
            try {

                val service = SecureHealthService.consultationService
                val data = service.updateSymptomAppointment(token,body)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun updateAppointment(token: String, body: Map<String, Any>): DataResponse? {
        return withContext(Dispatchers.IO) {
            try {

                val service = SecureHealthService.consultationService
                val data = service.updateAppointment(token,body)
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
package com.project.centennial.securecaremobileapp.repository

import android.util.Log
import com.project.centennial.securecaremobileapp.model.DataResponse
import com.project.centennial.securecaremobileapp.network.SecureHealthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class UserRepo {
    suspend fun login(body: Map<String, Any>) : DataResponse? {
        return withContext(Dispatchers.IO) {
            try {

                val service = SecureHealthService.userService

                val data = service.login(body)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun registerPatient(body: Map<String, Any>) : DataResponse? {
        return withContext(Dispatchers.IO) {
            try {


                val service = SecureHealthService.userService
                val data = service.addPatient(body)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun registerSpecialist(body: Map<String, Any>) : DataResponse? {
        return withContext(Dispatchers.IO) {
            try {

                val service = SecureHealthService.userService
                val data = service.addSpecialist(body)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun getProfile(token: String, userId: String, usertypeid: Int) : DataResponse? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("token: ", token)
                Log.d("userId: ", userId)
                Log.d("usertypeid: ", usertypeid.toString())

                val service = SecureHealthService.userService
                val data = service.getProfile(token,userId, usertypeid)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun updatePatient(token: String, body: Map<String, Any?>) : DataResponse? {
        return withContext(Dispatchers.IO) {
            try {

                val service = SecureHealthService.userService
                val data = service.updatePatient(token,body)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun updateSpecialist(token: String, body: Map<String, Any?>) : DataResponse? {
        return withContext(Dispatchers.IO) {
            try {

                val service = SecureHealthService.userService
                val data = service.updateSpecialist(token,body)
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
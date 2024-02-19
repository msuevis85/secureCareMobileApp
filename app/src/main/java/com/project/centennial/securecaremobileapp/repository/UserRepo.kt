package com.project.centennial.securecaremobileapp.repository

import android.util.Log
import android.widget.Toast
import com.project.centennial.securecaremobileapp.model.User
import com.project.centennial.securecaremobileapp.model.UserResponse
import com.project.centennial.securecaremobileapp.network.SecureHealthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class UserRepo {
    suspend fun login(email: String, password: String) : UserResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val body = mapOf(
                    "email" to email,
                    "password" to password
                )


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

    suspend fun register(user: User, password: String) : UserResponse? {
        return withContext(Dispatchers.IO) {
            try {

                val body = mapOf(
                    "usertypeid" to user.usertypeid,
                    "firstname" to user.firstname,
                    "lastname" to user.lastname,
                    "email" to user.email,
                    "phone" to user.phone,
                    "password" to password,
                    "address" to user.address,
                    "gender" to user.gender,
                    "dob" to user.dob
                )
                val service = SecureHealthService.userService
                val data = service.register(body)
                data
            } catch (ex: UnknownHostException) {
                return@withContext null
            } catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun getProfile(token: String, userId: String, usertypeid: Int) : UserResponse? {
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
}
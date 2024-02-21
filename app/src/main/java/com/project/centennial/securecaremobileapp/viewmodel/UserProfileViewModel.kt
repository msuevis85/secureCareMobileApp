package com.project.centennial.securecaremobileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.centennial.securecaremobileapp.model.User
import com.project.centennial.securecaremobileapp.model.UserResponse
import com.project.centennial.securecaremobileapp.repository.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserProfileViewModel: ViewModel() {

    private val userRepo: UserRepo = UserRepo()
    private val _userStateFlow = MutableStateFlow<UserResponse?>(null)
    val userStateFlow: StateFlow<UserResponse?> = _userStateFlow.asStateFlow()


    private val _updateProfileStateFlow = MutableStateFlow<UserResponse?>(null)
    val updateProfileStateFlow: StateFlow<UserResponse?> = _updateProfileStateFlow.asStateFlow()
    fun getProfile(token: String, userid: String, usertypeid: Int) {
        viewModelScope.launch {
            val user = userRepo.getProfile(token, userid,usertypeid)
            if (user != null) {
                _userStateFlow.update {
                    user
                }
            }
        }
    }

    fun updateProfile(token: String, body: Map<String, Any>) {
        viewModelScope.launch {
            val user = userRepo.updateProfile(token, body)
            if (user != null) {
                _updateProfileStateFlow.update {
                    user
                }
            }
        }
    }

}
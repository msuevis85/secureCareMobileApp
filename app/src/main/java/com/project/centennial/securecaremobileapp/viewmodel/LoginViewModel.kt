package com.project.centennial.securecaremobileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.centennial.securecaremobileapp.model.UserResponse
import com.project.centennial.securecaremobileapp.repository.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel: ViewModel() {

    private val userRepo: UserRepo = UserRepo()
    private val _userStateFlow = MutableStateFlow<UserResponse?>(null)
    val userStateFlow: StateFlow<UserResponse?> = _userStateFlow.asStateFlow()

    fun loginWithApi(email: String, password: String) {
        viewModelScope.launch {
            val user = userRepo.login(email, password)
            if (user != null) {
                _userStateFlow.update {
                    user
                }
            }
        }
    }

}
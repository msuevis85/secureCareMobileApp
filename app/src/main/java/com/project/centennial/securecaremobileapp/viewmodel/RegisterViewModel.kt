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

class RegisterViewModel: ViewModel() {

    private val userRepo: UserRepo = UserRepo()
    private val _userStateFlow = MutableStateFlow<UserResponse?>(null)
    val userStateFlow: StateFlow<UserResponse?> = _userStateFlow.asStateFlow()

    fun register(user: User, password: String) {
        viewModelScope.launch {
            val user = userRepo.register(user, password)
            if (user != null) {
                _userStateFlow.update {
                    user
                }
            }
        }
    }

}
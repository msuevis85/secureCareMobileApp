package com.project.centennial.securecaremobileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.centennial.securecaremobileapp.model.DataResponse
import com.project.centennial.securecaremobileapp.repository.UserRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel: ViewModel() {

    private val userRepo: UserRepo = UserRepo()
    private val _userStateFlow = MutableSharedFlow<DataResponse?>(0)
    val userStateFlow: SharedFlow<DataResponse?> = _userStateFlow.asSharedFlow()

    fun loginWithApi(body: Map<String, Any>) {
        viewModelScope.launch {
            val user = userRepo.login(body)
            if (user != null) {
                _userStateFlow.emit(user)
            }
        }
    }

}
package com.project.centennial.securecaremobileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.centennial.securecaremobileapp.model.DataArrayResponse
import com.project.centennial.securecaremobileapp.model.DataResponse
import com.project.centennial.securecaremobileapp.repository.ConsultationRepo
import com.project.centennial.securecaremobileapp.repository.UserRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    private val userRepo: UserRepo = UserRepo()
    private val consultationRepo: ConsultationRepo = ConsultationRepo()

    private val _userStateFlow = MutableSharedFlow<DataResponse?>(0)
    val userStateFlow: SharedFlow<DataResponse?> = _userStateFlow.asSharedFlow()

    private val _medSpecialtiesStateFlow = MutableSharedFlow<DataArrayResponse?>(0)
    val medSpecialtiesStateFlow: SharedFlow<DataArrayResponse?> = _medSpecialtiesStateFlow.asSharedFlow()

    fun registerPatient(body: Map<String, Any>) {
        viewModelScope.launch {
            val user = userRepo.registerPatient(body)
            if (user != null) {
                _userStateFlow.emit(user)
            }
        }
    }

    fun registerSpecialist(body: Map<String, Any>) {
        viewModelScope.launch {
            val user = userRepo.registerSpecialist(body)
            if (user != null) {
                _userStateFlow.emit(user)
            }
        }
    }

    fun getMedicalSpecialties() {
        viewModelScope.launch {
            val data = consultationRepo.getMedicalSpecialties()
            if (data != null) {
                _medSpecialtiesStateFlow.emit(data)
            }
        }
    }

}
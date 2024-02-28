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

class UserProfileViewModel: ViewModel() {

    private val userRepo: UserRepo = UserRepo()
    private val consultRepo: ConsultationRepo = ConsultationRepo()

    private val _medicalSpecialtiesStateFlow = MutableSharedFlow<DataArrayResponse?>(0)
    val medicalSpecialtiesStateFlow: SharedFlow<DataArrayResponse?> = _medicalSpecialtiesStateFlow.asSharedFlow()

    private val _userStateFlow = MutableSharedFlow<DataResponse?>(0)
    val userStateFlow: SharedFlow<DataResponse?> = _userStateFlow.asSharedFlow()


    private val _updateProfileStateFlow = MutableSharedFlow<DataResponse?>(0)
    val updateProfileStateFlow: SharedFlow<DataResponse?> = _updateProfileStateFlow.asSharedFlow()
    fun getProfile(token: String, userid: String, usertypeid: Int) {
        viewModelScope.launch {
            val user = usertypeid?.let { userRepo.getProfile(token, userid, it) }
            if (user != null) {
                _userStateFlow.emit(user)
            }
        }
    }

    fun updatePatient(token: String, body: Map<String, Any?>) {
        viewModelScope.launch {
            val user = userRepo.updatePatient(token, body)
            if (user != null) {
                _updateProfileStateFlow.emit(user)
            }
        }
    }

    fun updateSpecialist(token: String, body: Map<String, Any?>) {
        viewModelScope.launch {
            val user = userRepo.updateSpecialist(token, body)
            if (user != null) {
                _updateProfileStateFlow.emit(user)
            }
        }
    }

    fun getMedicalSpecialties() {
        viewModelScope.launch {
            val medicalSpecialties = consultRepo.getMedicalSpecialties()
            if (medicalSpecialties != null) {
                _medicalSpecialtiesStateFlow.emit(medicalSpecialties)
            }
        }
    }


}
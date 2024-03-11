package com.project.centennial.securecaremobileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.centennial.securecaremobileapp.model.DataArrayResponse
import com.project.centennial.securecaremobileapp.model.DataResponse
import com.project.centennial.securecaremobileapp.repository.ConsultationRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookAppointmentViewModel: ViewModel()  {

    private val consultationRepo: ConsultationRepo = ConsultationRepo()
    private var _specialistsStateFlow = MutableSharedFlow<DataArrayResponse?>(0)
    val specialistsStateFlow: SharedFlow<DataArrayResponse?> = _specialistsStateFlow.asSharedFlow()

    private var _schedulesStateFlow = MutableSharedFlow<DataArrayResponse?>(0)
    val schedulesStateFlow: SharedFlow<DataArrayResponse?> = _schedulesStateFlow.asSharedFlow()

    private var _appointmentStateFlow = MutableSharedFlow<DataResponse?>(0)
    val appointmentStateFlow: SharedFlow<DataResponse?> = _appointmentStateFlow.asSharedFlow()

    private var _medicalSpecialtiesStateFlow = MutableSharedFlow<DataArrayResponse?>(0)
    val medicalSpecialtiesStateFlow: SharedFlow<DataArrayResponse?> = _medicalSpecialtiesStateFlow.asSharedFlow()

    fun getSpecialists(token: String, medicalspecialtyid: Int) {
        viewModelScope.launch {
            val data = consultationRepo.getSpecialists(token, medicalspecialtyid)
            if (data != null) {
                _specialistsStateFlow.emit(data)
            }
        }
    }

    fun getSpecialistSchedules(token: String,
                               date: String,
                               specialistid: String) {
        viewModelScope.launch {
            val data = consultationRepo.getSpecialistSchedules(token,date,specialistid)
            if (data != null) {
                _schedulesStateFlow.emit(data)
            }
        }
    }

    fun bookAppointment(token: String, body: Map<String, Any?>) {
        viewModelScope.launch {
            val data = consultationRepo.bookAppointment(token,body)
            if (data != null) {
                _appointmentStateFlow.emit(data)
            }
        }
    }

    fun getMedicalSpecialties() {
        viewModelScope.launch {
            val data = consultationRepo.getMedicalSpecialties()
            if (data != null) {
                _medicalSpecialtiesStateFlow.emit(data)
            }
        }
    }
}
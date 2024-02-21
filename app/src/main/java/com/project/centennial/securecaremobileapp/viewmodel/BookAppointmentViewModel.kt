package com.project.centennial.securecaremobileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.centennial.securecaremobileapp.model.DataArrayResponse
import com.project.centennial.securecaremobileapp.model.DataResponse
import com.project.centennial.securecaremobileapp.repository.ConsultationRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookAppointmentViewModel: ViewModel()  {

    private val consultationRepo: ConsultationRepo = ConsultationRepo()
    private var _specialistsStateFlow = MutableStateFlow<DataArrayResponse?>(null)
    val specialistsStateFlow: StateFlow<DataArrayResponse?> = _specialistsStateFlow.asStateFlow()

    private var _schedulesStateFlow = MutableStateFlow<DataArrayResponse?>(null)
    val schedulesStateFlow: StateFlow<DataArrayResponse?> = _schedulesStateFlow.asStateFlow()

    private var _appointmentStateFlow = MutableStateFlow<DataResponse?>(null)
    val appointmentStateFlow: StateFlow<DataResponse?> = _appointmentStateFlow.asStateFlow()

    fun getSpecialists(token: String) {
        viewModelScope.launch {
            val data = consultationRepo.getSpecialists(token)
            if (data != null) {
                _specialistsStateFlow.update {
                    data
                }
            }
        }
    }

    fun getSpecialistSchedules(token: String,
                               date: String,
                               specialistid: String) {
        viewModelScope.launch {
            val data = consultationRepo.getSpecialistSchedules(token,date,specialistid)
            if (data != null) {
                _schedulesStateFlow.update {
                    data
                }
            }
        }
    }

    fun bookAppointment(token: String, body: Map<String, String>) {
        viewModelScope.launch {
            val data = consultationRepo.bookAppointment(token,body)
            if (data != null) {
                _appointmentStateFlow.update {
                    data
                }
            }
        }
    }
}
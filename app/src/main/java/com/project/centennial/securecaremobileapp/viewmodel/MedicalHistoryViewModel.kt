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

class MedicalHistoryViewModel: ViewModel() {
    private val consultationRepo: ConsultationRepo = ConsultationRepo()
    private val _appointmentsStateFlow = MutableSharedFlow<DataArrayResponse?>(0)
    val appointmentsStateFlow: SharedFlow<DataArrayResponse?> = _appointmentsStateFlow.asSharedFlow()

    private val _dataStateFlow = MutableSharedFlow<DataResponse?>(0)
    val dataStateFlow: SharedFlow<DataResponse?> = _dataStateFlow.asSharedFlow()

    private val _updateSymptomFlow = MutableSharedFlow<DataResponse?>(0)
    val updateSymptomFlow: SharedFlow<DataResponse?> = _updateSymptomFlow.asSharedFlow()

    private val _updateAppointmentFlow = MutableSharedFlow<DataResponse?>(0)
    val updateAppointmentFlow: SharedFlow<DataResponse?> = _updateAppointmentFlow.asSharedFlow()
    fun getAppointments(token: String, userid: String) {
        viewModelScope.launch {
            val data = consultationRepo.getAppointmentsByUserId(token, userid)
            if (data != null) {
                _appointmentsStateFlow.emit(data)
            }
        }
    }

    fun cancelAppointment(token: String, appointmentid: String) {
        viewModelScope.launch {
            val data = consultationRepo.cancelAppointment(token, appointmentid)
            if (data != null) {
                _dataStateFlow.emit(data)
            }
        }
    }

    fun updateSymptom(token: String, body: Map<String, Any>) {
        viewModelScope.launch {
            val data = consultationRepo.updateSymptom(token, body)
            if (data != null) {
                _updateSymptomFlow.emit(data)
            }
        }
    }

    fun updateAppointment(token: String, body: Map<String, Any>) {
        viewModelScope.launch {
            val data = consultationRepo.updateAppointment(token, body)
            if (data != null) {
                _updateAppointmentFlow.emit(data)
            }
        }
    }
}
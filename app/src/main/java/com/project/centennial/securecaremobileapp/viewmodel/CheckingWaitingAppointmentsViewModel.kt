package com.project.centennial.securecaremobileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.centennial.securecaremobileapp.model.DataArrayResponse
import com.project.centennial.securecaremobileapp.model.DataResponse
import com.project.centennial.securecaremobileapp.repository.SpecialistRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CheckingWaitingAppointmentsViewModel: ViewModel() {
    private val specialistRepo: SpecialistRepo = SpecialistRepo()

    private var _appointmentsFlow = MutableSharedFlow<DataArrayResponse?>(0)
    val appointmentsFlow: SharedFlow<DataArrayResponse?> = _appointmentsFlow.asSharedFlow()

    private var _confirmedAppointmentFlow = MutableSharedFlow<DataResponse?>(0)
    val confirmedAppointmentFlow: SharedFlow<DataResponse?> = _confirmedAppointmentFlow.asSharedFlow()

    fun getWaitingAppointments(token: String, userid: String) {
        viewModelScope.launch {
            val data = specialistRepo.getWaitingAppointments(token, userid)
            if (data != null) {
                _appointmentsFlow.emit(data)
            }
        }
    }

    fun confirmAppointment(token: String, body: Map<String, Any>) {
        viewModelScope.launch {
            viewModelScope.launch {
                val data = specialistRepo.confirmAppointment(token, body)
                if (data != null) {
                    _confirmedAppointmentFlow.emit(data)
                }
            }
        }
    }
}
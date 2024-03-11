package com.project.centennial.securecaremobileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.centennial.securecaremobileapp.model.DataArrayResponse
import com.project.centennial.securecaremobileapp.model.DataResponse
import com.project.centennial.securecaremobileapp.repository.ConsultationRepo
import com.project.centennial.securecaremobileapp.repository.SpecialistRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ShowSpecialistScheduleViewModel: ViewModel() {
    private val specialistRepo: SpecialistRepo = SpecialistRepo()
    private var _schedulesStateFlow = MutableSharedFlow<DataArrayResponse?>(0)
    val schedulesStateFlow: SharedFlow<DataArrayResponse?> = _schedulesStateFlow.asSharedFlow()


    fun getSchedulesByDate(token: String, userid: String, date:String) {
        viewModelScope.launch {
            val data = specialistRepo.getSchedulesByDate(token, userid, date)
            if (data != null) {
                _schedulesStateFlow.emit(data)
            }
        }
    }



}
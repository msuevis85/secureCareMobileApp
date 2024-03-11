package com.project.centennial.securecaremobileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.centennial.securecaremobileapp.model.DataArrayResponse
import com.project.centennial.securecaremobileapp.repository.ConsultationRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchSpecialistViewModel: ViewModel() {

    private val consultationRepo: ConsultationRepo = ConsultationRepo()
    private val _specialistsStateFlow = MutableSharedFlow<DataArrayResponse?>(0)
    val specialistsStateFlow: SharedFlow<DataArrayResponse?> = _specialistsStateFlow.asSharedFlow()


    fun searchOnSpecialist(searchTerm: String) {
        viewModelScope.launch {
            val data = consultationRepo.searchSpecialists(searchTerm)
            if (data != null) {
                _specialistsStateFlow.emit(data)
            }
        }
    }
}
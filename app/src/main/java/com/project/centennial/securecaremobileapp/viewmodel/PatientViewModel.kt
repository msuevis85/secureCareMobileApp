package com.project.centennial.securecaremobileapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Patient
import com.project.centennial.securecaremobileapp.repository.PatientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PatientViewModel( private val patientRepository: PatientRepository): ViewModel() {

    fun getAllPatient(): Flow<List<Patient>> = patientRepository.getAll

     fun findById(patientId: Int): Patient = patientRepository.findById(patientId)

     fun update(patient: Patient) = viewModelScope.launch(Dispatchers.IO){
        patientRepository.update(patient)
    }

     fun insert(patient: Patient) = viewModelScope.launch(Dispatchers.IO) {
        patientRepository.insert(patient)
    }

    fun delete(patient: Patient) = viewModelScope.launch(Dispatchers.IO){
        patientRepository.delete(patient)
    }



    class PatientViewModelFactory(
        private val patientRepository: PatientRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PatientViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PatientViewModel(patientRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
package com.project.centennial.securecaremobileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Nurse
import com.project.centennial.securecaremobileapp.repository.NurseRepository
import kotlinx.coroutines.flow.Flow

class NurseViewModel( private val nurseRepository: NurseRepository): ViewModel() {

    fun getAllNurses(): Flow<List<Nurse>> = nurseRepository.getAllNurses

    fun login(user: Int, password : String): Nurse = nurseRepository.login(user,password)


    class NurseViewModelFactory(
        private val nurseRepository: NurseRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NurseViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NurseViewModel(nurseRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


}
package com.project.centennial.securecaremobileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Nurse
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Patient
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Test
import com.project.centennial.securecaremobileapp.repository.NurseRepository
import com.project.centennial.securecaremobileapp.repository.TestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class TestViewModel( private val testRepository: TestRepository): ViewModel() {
    fun getAllPatientTest(patientId: Int): Flow<List<Test>> = testRepository.getAllPatientTest(patientId)

    fun insert(test: Test) = viewModelScope.launch(Dispatchers.IO) {
        testRepository.insert(test)
    }

        class TestViewModelFactory(
            private val testRepository: TestRepository
        ) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return TestViewModel(testRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }


    }
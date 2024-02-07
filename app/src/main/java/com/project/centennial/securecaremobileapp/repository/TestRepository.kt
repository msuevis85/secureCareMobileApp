package com.project.centennial.securecaremobileapp.repository

import androidx.annotation.WorkerThread
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Nurse
import com.project.centennial.securecaremobileapp.database.secureCareMobile.NurseDao
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Patient
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Test
import com.project.centennial.securecaremobileapp.database.secureCareMobile.TestDao
import kotlinx.coroutines.flow.Flow

class TestRepository(private val testDao: TestDao) {


    @WorkerThread
    fun getAllPatientTest(patientId: Int): Flow<List<Test>> {
        return testDao.getAllPatientTest(patientId)
    }

    @WorkerThread
    fun insert(test: Test) {
        testDao.insert(test)
    }
}
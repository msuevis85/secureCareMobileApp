package com.project.centennial.securecaremobileapp.repository

import androidx.annotation.WorkerThread
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Patient
import com.project.centennial.securecaremobileapp.database.secureCareMobile.PatientDao

import kotlinx.coroutines.flow.Flow

class PatientRepository(private val patientDao: PatientDao) {

    val getAll : Flow<List<Patient>> = patientDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun update(patient: Patient){
        patientDao.update(patient)
    }

    @WorkerThread
   fun findById(patientId: Int): Patient{
        return patientDao.findById(patientId)
    }

    @WorkerThread
    fun insert(patient: Patient) {
        patientDao.insert(patient)
    }

    @WorkerThread
    fun delete(patient: Patient){
        patientDao.delete(patient)
    }
}
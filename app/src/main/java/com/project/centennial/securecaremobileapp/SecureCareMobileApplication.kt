package com.project.centennial.securecaremobileapp

import android.app.Application
import com.project.centennial.securecaremobileapp.database.AppDatabase
import com.project.centennial.securecaremobileapp.repository.NurseRepository
import com.project.centennial.securecaremobileapp.repository.PatientRepository
import com.project.centennial.securecaremobileapp.repository.TestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class SecureCareMobileApplication: Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this) }
    val patientRepository by lazy { PatientRepository(database.patientDao()) }
    val nurseRepository by lazy { NurseRepository(database.nurseDao()) }
    val testRepository by lazy { TestRepository(database.testDao()) }

}
package com.project.centennial.securecaremobileapp.repository

import com.project.centennial.securecaremobileapp.database.secureCareMobile.Nurse
import com.project.centennial.securecaremobileapp.database.secureCareMobile.NurseDao

import kotlinx.coroutines.flow.Flow

class NurseRepository(private val nurseDao: NurseDao) {

    val getAllNurses : Flow<List<Nurse>> = nurseDao.getAllNurses()

    fun login(user:Int,password:String): Nurse {
        return nurseDao.login(user, password)
    }
}
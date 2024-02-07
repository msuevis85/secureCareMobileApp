package com.project.centennial.securecaremobileapp.database.secureCareMobile


import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NurseDao {
    @Query("Select * from Nurse")
    fun getAllNurses() : Flow<List<Nurse>>

    @Query("Select * from Nurse WHERE nurseId = :user AND password = :password LIMIT 1")
    fun login(user: Int, password: String) : Nurse
}
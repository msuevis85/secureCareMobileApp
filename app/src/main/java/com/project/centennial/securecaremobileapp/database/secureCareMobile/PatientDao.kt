package com.project.centennial.securecaremobileapp.database.secureCareMobile


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Query("Select * from Patient")
    fun getAll() : Flow<List<Patient>>

    @Query("Select * from Patient where patientId == :patienId limit 1")
    fun findById(patienId: Int): Patient

    @Update
    fun update(patient : Patient)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(patient: Patient)

    @Delete
    fun delete(patient:Patient)
}
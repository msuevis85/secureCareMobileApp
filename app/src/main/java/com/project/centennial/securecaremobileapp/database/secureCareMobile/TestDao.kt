package com.project.centennial.securecaremobileapp.database.secureCareMobile


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {

    @Query("Select * from Test WHERE patientId == :patientId")
    fun getAllPatientTest(patientId: Int) : Flow<List<Test>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(test: Test)
}
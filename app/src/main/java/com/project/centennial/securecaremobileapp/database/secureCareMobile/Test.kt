package com.project.centennial.securecaremobileapp.database.secureCareMobile


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Test")
data class Test(
    @PrimaryKey(autoGenerate = true) var testId: Int,
    @ColumnInfo(name = "patientId") var patientId: Int,
    @ColumnInfo(name = "nurseId") var nurseId: Int,
    @ColumnInfo(name = "BPL") var BPL: Double,
    @ColumnInfo(name = "BPH") var BPH: Double,
    @ColumnInfo(name = "temperature") var temperature: Double
)

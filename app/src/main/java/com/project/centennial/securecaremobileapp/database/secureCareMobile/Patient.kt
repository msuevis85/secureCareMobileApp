package com.project.centennial.securecaremobileapp.database.secureCareMobile


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Patient")
data class Patient(
    @PrimaryKey(autoGenerate = true) var patientId: Int,
    @ColumnInfo(name = "firstname") var firstname: String,
    @ColumnInfo(name = "lastname") var lastname: String,
    @ColumnInfo(name = "department") var department: String,
    @ColumnInfo(name = "nurseId") var nurseId: Int,
    @ColumnInfo(name = "room") var room: Int
)
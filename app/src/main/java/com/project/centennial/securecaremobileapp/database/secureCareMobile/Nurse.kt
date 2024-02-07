package com.project.centennial.securecaremobileapp.database.secureCareMobile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Nurse")
data class Nurse(
    @PrimaryKey val nurseId: Int,
    @ColumnInfo(name = "firstname") var firstname: String,
    @ColumnInfo(name = "lastname") var lastname: String,
    @ColumnInfo(name = "department") var department: String,
    @ColumnInfo(name = "password") var password: String,
)
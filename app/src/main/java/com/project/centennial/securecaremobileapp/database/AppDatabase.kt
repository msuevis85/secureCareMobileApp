package com.project.centennial.securecaremobileapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Nurse
import com.project.centennial.securecaremobileapp.database.secureCareMobile.NurseDao
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Patient
import com.project.centennial.securecaremobileapp.database.secureCareMobile.PatientDao
import com.project.centennial.securecaremobileapp.database.secureCareMobile.Test
import com.project.centennial.securecaremobileapp.database.secureCareMobile.TestDao

/**
 * Defines a database and specifies data tables that will be used.
 * Version is incremented as new tables/columns are added/removed/changed.
 * You can optionally use this class for one-time setup, such as pre-populating a database.
 *
 */

@Database(entities = [Patient::class, Nurse::class, Test::class], version=1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun nurseDao(): NurseDao
    abstract fun testDao(): TestDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .createFromAsset("hospital.db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}

package com.project.centennial.securecaremobileapp.utils

enum class AppointmentStatus (val id: String){
    Confirmed("Confirmed"),
    Waiting("Waiting"),
    Cancel("Cancelled"),
    Completed("Completed");

    companion object {
        fun fromId(id: String): AppointmentStatus? {
            return values().find { it.id == id }
        }
    }
}
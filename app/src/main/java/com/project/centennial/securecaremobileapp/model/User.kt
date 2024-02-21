package com.project.centennial.securecaremobileapp.model

import java.util.Date

data class User(
    val userid: String,
    val usertypeid: Int,
    val email: String,
    val firstname: String,
    val lastname: String,
    val address: String = "",
    val phone: String = "",
    var gender: String = "",
    val dob: String
)

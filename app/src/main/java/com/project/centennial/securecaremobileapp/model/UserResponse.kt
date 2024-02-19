package com.project.centennial.securecaremobileapp.model

data class UserResponse(
    val status: Boolean,
    val message: String,
    val token: String,
    val user: User
)

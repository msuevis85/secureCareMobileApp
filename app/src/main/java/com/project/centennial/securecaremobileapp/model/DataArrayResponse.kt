package com.project.centennial.securecaremobileapp.model

data class DataArrayResponse(
    val status: Boolean,
    val message: String,
    val token: String,
    val data: List<Map<String, Any>>
)

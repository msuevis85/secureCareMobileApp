package com.project.centennial.securecaremobileapp.model

data class DataResponse(
    val status: Boolean,
    val message: String,
    val token: String,
    val data: Map<String, Any>
)

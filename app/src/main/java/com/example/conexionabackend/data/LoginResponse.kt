package com.example.conexionabackend.data

data class LoginResponse(
    val token: String,
    val message: String? = null
)

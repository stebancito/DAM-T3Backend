package com.example.conexionabackend.network

import com.example.conexionabackend.data.ApiResponse
import com.example.conexionabackend.data.LoginRequest
import com.example.conexionabackend.data.RegisterRequest
import retrofit2.http.*

interface ApiService {
    @GET("/")
    suspend fun getRoot(): ApiResponse

    @POST("/register")
    suspend fun register(@Body request: RegisterRequest): ApiResponse

    @POST("/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse
}
package com.example.oscarapi.API

interface OscarApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("votos/confirmar")
    fun confirmarVoto(@Body request: VotoRequest): Call<ApiResponse>
}

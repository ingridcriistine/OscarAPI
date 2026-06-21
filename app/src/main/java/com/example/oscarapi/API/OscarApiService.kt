package com.example.oscarapi.API

import com.example.oscarapi.dtos.ApiResponse
import com.example.oscarapi.dtos.LoginRequest
import com.example.oscarapi.dtos.LoginResponse
import com.example.oscarapi.dtos.VotoRequest
import com.example.oscarapi.model.Filme
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface OscarApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("votos/confirmar")
    fun confirmarVoto(@Body request: VotoRequest): Call<ApiResponse>

    @GET
    suspend fun listarFilmes(@Url url: String): List<Filme>
}

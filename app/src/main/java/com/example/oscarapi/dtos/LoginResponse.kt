package com.example.oscarapi.dtos

data class LoginResponse(
    val usuarioId: Long,
    val token: Int,
    val mensagem: String
)

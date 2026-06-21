package com.example.oscarapi.dtos

data class VotoRequest(
    val usuarioId: Long,
    val filmeId: String,
    val diretorId: String,
    val token: Int
)

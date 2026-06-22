package com.example.oscarapi.dtos

data class VotoRequest(
    val usuarioId: Long,
    val filmeId: String,
    val diretorId: Long,
    val token: Int
)

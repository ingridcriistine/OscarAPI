package com.example.oscarapi

object SessaoVoto {
    var usuarioId: Long = -1L
    var usuarioNome: String? = null
    var token: Int? = null
    
    var filmeId: Long? = null
    var filmeNome: String? = null
    var diretorId: Long? = null
    var diretorNome: String? = null
    
    var confirmado: Boolean = false

    fun limpar() {
        usuarioId = -1L
        usuarioNome = null
        token = null
        filmeId = null
        filmeNome = null
        diretorId = null
        diretorNome = null
        confirmado = false
    }
}

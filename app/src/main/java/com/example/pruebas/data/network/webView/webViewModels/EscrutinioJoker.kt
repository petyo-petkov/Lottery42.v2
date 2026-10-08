package com.example.pruebas.data.network.webView.webViewModels

import kotlinx.serialization.Serializable

@Serializable
data class EscrutinioJoker(
    val ganadores: Int,
    val orden_escrutinio: String,
    val premio: String,
    val tipo: String
)

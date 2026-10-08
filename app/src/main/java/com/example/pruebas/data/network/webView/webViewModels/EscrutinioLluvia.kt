package com.example.pruebas.data.network.webView.webViewModels

import kotlinx.serialization.Serializable

@Serializable
data class EscrutinioLluvia(
    val tipo: String,
    val ganadores: Int,
    val premio: String,
    val ganadores_eu: Int,
    val orden_escrutinio: String
)
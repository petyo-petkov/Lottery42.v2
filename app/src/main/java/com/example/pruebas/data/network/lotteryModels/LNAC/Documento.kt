package com.example.pruebas.data.network.lotteryModels.LNAC


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Documento(
    @SerialName("tituloContenido")
    val tituloContenido: String? = null,
    @SerialName("tituloRelacion")
    val tituloRelacion: String? = null,
    @SerialName("urlContenido")
    val urlContenido: String? = null
)
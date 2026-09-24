package com.example.pruebas.data.network.lotteryModels.LNAC


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Noticia(
    @SerialName("tituloContenido")
    val tituloContenido: String?,
    @SerialName("tituloRelacion")
    val tituloRelacion: String?,
    @SerialName("urlContenido")
    val urlContenido: String?
)
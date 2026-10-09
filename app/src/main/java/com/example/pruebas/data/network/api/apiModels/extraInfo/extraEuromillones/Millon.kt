package com.example.pruebas.data.network.api.apiModels.extraInfo.extraEuromillones


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Millon(
    @SerialName("activo")
    val activo: String,
    @SerialName("combinacion")
    val combinacion: String,
    @SerialName("contenidosRelacionados")
    val contenidosRelacionados: ContenidosRelacionados,
    @SerialName("gameid")
    val gameid: String,
    @SerialName("importe")
    val importe: Int,
    @SerialName("relsorteoid_asociado")
    val relsorteoidAsociado: String
)
package com.example.pruebas.data.network.api.apiModels.extraInfo.extraInfoSorteos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Joker(
    @SerialName("activo")
    val activo: String,
    @SerialName("bote_joker")
    val boteJoker: Int,
    @SerialName("combinacion")
    val combinacion: String,
    @SerialName("gameid")
    val gameid: String,
    @SerialName("relsorteoid_asociado")
    val relsorteoidAsociado: String
)

package com.example.pruebas.data.network.api.apiModels.extraInfo.extraInfoSorteos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EscrutinioMillon(
    @SerialName("ganadores")
    val ganadores: Int,
    @SerialName("orden_escrutinio")
    val ordenEscrutinio: String,
    @SerialName("premio")
    val premio: String,
    @SerialName("tipo")
    val tipo: String
)

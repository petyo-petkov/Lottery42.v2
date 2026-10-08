package com.example.pruebas.data.network.api.apiModels.proximosNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Metadata(
    @SerialName("cdc")
    val cdc: String,
    @SerialName("destacarBote")
    val destacarBote: Boolean,
    @SerialName("lugar")
    val lugar: String,
    @SerialName("nombre")
    val nombre: String,
    @SerialName("precio")
    val precio: Int,
    @SerialName("primerPremio")
    val primerPremio: Int
)
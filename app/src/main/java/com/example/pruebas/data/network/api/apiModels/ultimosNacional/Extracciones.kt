package com.example.pruebas.data.network.api.apiModels.ultimosNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Extracciones(
    @SerialName("cuatroCifras")
    val cuatroCifras: List<CuatroCifra>,
    @SerialName("dosCifras")
    val dosCifras: List<DosCifra>,
    @SerialName("tresCifras")
    val tresCifras: List<TresCifra>
)
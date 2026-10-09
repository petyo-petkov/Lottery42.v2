package com.example.pruebas.data.network.api.apiModels.extraInfo.extraNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultData(
    @SerialName("cuartosPremios")
    val cuartosPremios: List<CuartoPremio?> = emptyList(),
    @SerialName("extracciones")
    val extracciones: Extracciones,
    @SerialName("primerPremio")
    val primerPremio: PrimerPremio,
    @SerialName("quintosPremios")
    val quintosPremios: List<QuintoPremio?> = emptyList(),
    @SerialName("reintegros")
    val reintegros: List<Reintegro>,
    @SerialName("segundoPremio")
    val segundoPremio: SegundoPremio,
    @SerialName("tercerosPremios")
    val tercerosPremios: List<TercerPremio?> = emptyList()
)
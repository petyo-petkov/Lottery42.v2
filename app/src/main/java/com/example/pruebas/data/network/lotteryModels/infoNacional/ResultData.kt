package com.example.pruebas.data.network.lotteryModels.infoNacional


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultData(
    @SerialName("cuartosPremios")
    val cuartosPremios: List<CuartosPremio?>?,
    @SerialName("primerPremio")
    val primerPremio: PrimerPremio?,
    @SerialName("quintosPremios")
    val quintosPremios: List<QuintosPremio?>?,
    @SerialName("reintegros")
    val reintegros: List<Reintegro>?,
    @SerialName("segundoPremio")
    val segundoPremio: SegundoPremio?,
    @SerialName("tercerosPremios")
    val tercerosPremios: List<TercerosPremio>?
)
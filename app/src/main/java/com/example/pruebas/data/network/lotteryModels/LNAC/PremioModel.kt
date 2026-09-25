package com.example.pruebas.data.network.lotteryModels.LNAC


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PremioModel(
    @SerialName("codigoPremio")
    val codigoPremio: String? = null,
    @SerialName("decimo")
    val decimo: String?,
    @SerialName("fraccion")
    val fraccion: Int? = null,
    @SerialName("idSorteo")
    val idSorteo: String?,
    @SerialName("precioDecimoEnCentimos")
    val precioDecimoEnCentimos: Int?,
    @SerialName("premioEnCentimos")
    val premioEnCentimos: Int?,
    @SerialName("serie")
    val serie: Int? = null
)
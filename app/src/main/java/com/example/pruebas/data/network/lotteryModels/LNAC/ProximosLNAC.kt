package com.example.pruebas.data.network.lotteryModels.LNAC


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProximosLNAC(
    @SerialName("anyo")
    val anyo: String?,
    @SerialName("apertura")
    val apertura: String?,
    @SerialName("cdc")
    val cdc: String?,
    @SerialName("cierre")
    val cierre: String?,
    @SerialName("destacar_bote")
    val destacarBote: String?,
    @SerialName("dia_semana")
    val diaSemana: String?,
    @SerialName("estado")
    val estado: String?,
    @SerialName("fecha")
    val fecha: String?,
    @SerialName("game_id")
    val gameId: String?,
    @SerialName("id_sorteo")
    val idSorteo: String?,
    @SerialName("lugar")
    val lugar: String?,
    @SerialName("nombre")
    val nombre: String?,
    @SerialName("precio")
    val precio: Int?,
    @SerialName("premio_bote")
    val premioBote: Int? = null,
    @SerialName("premio_especial")
    val premioEspecial: Int? = null,
    @SerialName("primer_premio")
    val primerPremio: Int?,
    @SerialName("recaudacion")
    val recaudacion: Int? = null
)
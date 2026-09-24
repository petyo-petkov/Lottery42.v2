package com.example.pruebas.data.network.lotteryModels.LNAC


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UltimosLNAC(
    @SerialName("anyo")
    val anyo: String?,
    @SerialName("apertura")
    val apertura: String?,
    @SerialName("apuestas")
    val apuestas: String?,
    @SerialName("cdc")
    val cdc: String?,
    @SerialName("cierre")
    val cierre: String?,
    @SerialName("contenidosRelacionados")
    val contenidosRelacionados: ContenidosRelacionados?,
    @SerialName("cuartosPremios")
    val cuartosPremios: List<String?>? = emptyList(),
    @SerialName("dia_semana")
    val diaSemana: String?,
    @SerialName("escrutinio")
    val escrutinio: List<Escrutinio>?,
    @SerialName("estado")
    val estado: String?,
    @SerialName("extraccionesDeCincoCifras")
    val extraccionesDeCincoCifras: List<String?>? = emptyList(),
    @SerialName("extraccionesDeCuatroCifras")
    val extraccionesDeCuatroCifras: List<ExtraccionesDeCuatroCifra>?,
    @SerialName("extraccionesDeDosCifras")
    val extraccionesDeDosCifras: List<ExtraccionesDeDosCifra>?,
    @SerialName("extraccionesDeTresCifras")
    val extraccionesDeTresCifras: List<ExtraccionesDeTresCifra>?,
    @SerialName("fecha_sorteo")
    val fechaSorteo: String?,
    @SerialName("fondo_bote")
    val fondoBote: String?,
    @SerialName("game_id")
    val gameId: String?,
    @SerialName("id_sorteo")
    val idSorteo: String?,
    @SerialName("lugar")
    val lugar: String?,
    @SerialName("modelDraw")
    val modelDraw: Int?,
    @SerialName("mostrarConsignacion")
    val mostrarConsignacion: Boolean?,
    @SerialName("mostrarVentas")
    val mostrarVentas: Boolean?,
    @SerialName("nombre")
    val nombre: String?,
    @SerialName("num_sorteo")
    val numSorteo: String?,
    @SerialName("precioDecimo")
    val precioDecimo: Int?,
    @SerialName("premio_bote")
    val premioBote: String?,
    @SerialName("premio_especial")
    val premio_especial: Int? = null,
    @SerialName("premioEspecial")
    val premioEspecial: PremioEspecial?,
    @SerialName("premios")
    val premios: String?,
    @SerialName("primer_premio")
    val primer_premio: Int?,
    @SerialName("primerPremio")
    val primerPremio: PrimerPremio?,
    @SerialName("quintosPremios")
    val quintosPremios: List<Int?>? = emptyList(),
    @SerialName("recaudacion")
    val recaudacion: String?,
    @SerialName("reintegros")
    val reintegros: List<Reintegro>?,
    @SerialName("segundoPremio")
    val segundoPremio: SegundoPremio?,
    @SerialName("tercerosPremios")
    val tercerosPremios: List<String?>? = emptyList(),
    @SerialName("tipoSorteo")
    val tipoSorteo: String?,
    @SerialName("tipoSorteoGeneral")
    val tipoSorteoGeneral: String?,
    @SerialName("urlListadoOficial")
    val urlListadoOficial: String?
)
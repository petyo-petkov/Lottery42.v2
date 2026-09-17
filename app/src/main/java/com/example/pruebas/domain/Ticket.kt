package com.example.pruebas.domain

import com.example.pruebas.data.db.TicketEntity


data class Ticket(
    val id: String = "",
    val drawId: String = "",                           // A = 1321902020120280171942842258210547
    val gameType: String = "",                         // P = 2
    val name: String = "",
    val cdc: String = "",
    val drawDate: String = "",                         // S = 251 08SEP26 :1
    val gameStatus: String = "",                       // W = 0
    val office: String = "",                           // T = 50050-1
    val numbers: List<String> = emptyList(),           // .1 = 040812273049.2 = 041519274249
    val prize: String = "0.0",                          // Ganancia
    val betPrice: String = "0.0",                       // Precio
    val isWinner: Boolean = false,

    //Primitiva
    val joker: String? = null,                          // J = NO
    val reintegro: String? = null,                      // R = 2

    //Euromillones
    val stars: List<String>? = emptyList(),             // .1 = 021113263338:0105
    val millon: String? = null,                         // RI=11[S=07311SEP26:1,FXX92887];

    // Eurodreams
    val dreams: List<String>? = emptyList(),

    // Loteria Nacional
    val numLottery: String? = null,                     // N = 81030
    val serie: String? = null,                          // SE = 5
    val fraccion: String? = null,                       // F = 1

    //El Gordo
    val clave: List<String>? = emptyList()


) {

    fun toEntity(): TicketEntity {

        return TicketEntity(
            id = id,
            drawId = drawId,
            gameType = gameType,
            name = name,
            cdc = cdc,
            drawDate = drawDate,
            gameStatus = gameStatus,
            office = office,
            numbers = numbers,
            prize = prize,
            betPrice = betPrice,
            isWinner = isWinner,
            joker = joker,
            reintegro = reintegro,
            stars = stars,
            millon = millon,
            dreams = dreams,
            numLottery = numLottery,
            serie = serie,
            fraccion = fraccion,
            clave = clave,
        )
    }
}

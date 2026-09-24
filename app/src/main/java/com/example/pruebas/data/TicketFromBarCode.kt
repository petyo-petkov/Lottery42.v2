package com.example.pruebas.data

import com.example.pruebas.domain.NetworkRepo
import com.example.pruebas.domain.Ticket

suspend fun ticketFromBarCode(rawData: String, networkRepo: NetworkRepo) : Ticket {

    val id = rawData.take(10)
    val gameType = "LNAC"
    val name = "Primitiva"
    val numSorteo: String = rawData.substring(1..3)
    val numDecimo: String = rawData.substring(11..16)


    val info = networkRepo.getInfoProximosLNAC()


    return Ticket(
        id = id,
        drawId = "drawId",
        gameType = gameType,
        name = name,
        numeroSorteo = "cdc",
        drawDate = "date",
        gameStatus = "gameStatus",
        office = "office",
        numbers = emptyList(),
        prize = "0.0",
        betPrice = "betPrice.toString()",
        isWinner = false,
        joker = "",
        reintegro = "",
        stars = emptyList(),
        millon = "",
        dreams = emptyList(),
        numDecimo = numDecimo,
        serie = "serie",
        fraccion = "fraccion",
        clave = emptyList()


    )

}
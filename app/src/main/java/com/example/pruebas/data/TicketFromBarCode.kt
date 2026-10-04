package com.example.pruebas.data

import com.example.pruebas.domain.Ticket
import com.example.pruebas.domain.WebViewRepo

suspend fun ticketFromBarCode(
    rawData: String,
    webViewRepo: WebViewRepo,
): Ticket {

    val id = rawData.take(20)
    val numSorteo: String = rawData.substring(1..3)
    val numDecimo: String = rawData.substring(11..15)
    val serie = rawData.substring(7..9)
    val fraccion = rawData.substring(5..6)

    val missingInfo = webViewRepo.getMissingInfoLNAC(numSorteo)

    return Ticket(
        id = id,
        drawId = missingInfo?.drawId ?: "",
        cdc = missingInfo?.drawId?.take(5) ?: "",
        gameType = "nacional",
        name = "Loteria Nacional",
        numeroSorteo = numSorteo,
        drawDate = missingInfo?.drawDate ?: "",
        gameStatus = missingInfo?.gameStatus ?: "",
        office = "office?",
        numbers = emptyList(),
        prize = "0.0",
        betPrice = missingInfo?.precio ?: "0.0",
        isWinner = false,
        numDecimo = numDecimo,
        serie = serie,
        fraccion = fraccion,
    )

}


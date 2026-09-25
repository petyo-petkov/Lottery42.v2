package com.example.pruebas.data

import android.util.Log
import com.example.pruebas.domain.NetworkRepo
import com.example.pruebas.domain.Ticket
import com.example.pruebas.domain.WebViewRepo

suspend fun ticketFromBarCode(rawData: String, webViewRepo: WebViewRepo) : Ticket {

    val id = rawData.take(10)
    val gameType = "LNAC"
    val name = "Loteria Nacional"
    val numSorteo: String = rawData.substring(1..3)
    val numDecimo: String = rawData.substring(11..15)

    val fetchData = try {
        webViewRepo.getInfoLNAC(numSorteo, gameType)
    }catch (e: Exception){
        Log.e("NETWORK ERROR","Error al obtener info del sorteo, en crear desde barcode:  ${e.message}")
        null
    }

    val drawId = fetchData?.idSorteo ?: ""
    val cdc = fetchData?.idSorteo?.take(5) ?: ""
    val fecha = fetchData?.fecha ?: ""
    val precio = fetchData?.precio ?: "0.0"


    return Ticket(
        id = id,
        drawId = drawId,
        gameType = gameType,
        name = name,
        numeroSorteo = cdc,
        drawDate = fecha,
        gameStatus = "gameStatus",
        office = "office",
        numbers = emptyList(),
        prize = "0.0",
        betPrice = precio,
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


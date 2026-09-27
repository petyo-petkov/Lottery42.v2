package com.example.pruebas.data

import com.example.pruebas.domain.NetworkRepo
import com.example.pruebas.domain.Ticket
import com.example.pruebas.domain.WebViewRepo

suspend fun ticketFromBarCode(
    rawData: String,
    webViewRepo: WebViewRepo,
    networkRepo: NetworkRepo
): Ticket {

    val id = rawData.take(10)
    val numSorteo: String = rawData.substring(1..3)
    val numDecimo: String = rawData.substring(11..15)
    val serie = rawData.substring(7..9)
    val fraccion = rawData.substring(5..6)
    val type = rawData.take(1)

//    val fetchData = try {
//        webViewRepo.getInfoLNAC(numSorteo, gameType)
//    }catch (e: Exception){
//        Log.e("NETWORK ERROR","Error al obtener info del sorteo, en crear desde barcode:  ${e.message}")
//        null
//    }

//    val name = "Loteria Nacional"
//    val gameType = "LNAC"
//    val drawId = fetchData?.idSorteo ?: ""
//    val cdc = fetchData?.idSorteo?.take(5) ?: ""
//    val fecha = fetchData?.fecha ?: ""
//    val precio = fetchData?.precio ?: "0.0"

    val respond = networkRepo.getInfoNacional(numSorteo)
    val data = respond.data?.lastOrNull()

    val name = data?.game?.name ?: ""
    val gameType = data?.game?.slug ?:""
    val drawId = data?.drawId ?: ""
    val cdc = data?.drawId?.take(5) ?: ""
    val fecha = data?.drawDate ?:""
    val precio = (data?.resultData?.reintegros?.firstOrNull()?.prize )?.div(100).toString()
    val gameStatus = data?.status ?: ""




    return Ticket(
        id = id,
        drawId = drawId,
        cdc = cdc,
        gameType = gameType,
        name = name,
        numeroSorteo = numSorteo,
        drawDate = fecha,
        gameStatus = gameStatus,
        office = "office?",
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
        serie = serie,
        fraccion = fraccion,
        clave = emptyList()


    )

}


package com.example.pruebas.domain

import com.example.pruebas.data.network.InfoLNAC
import com.example.pruebas.data.network.lotteryModels.LNAC.ProximosLNAC
import com.example.pruebas.data.network.lotteryModels.LNAC.UltimosLNAC
import com.example.pruebas.data.network.lotteryModels.checkModel.CheckModel
import com.example.pruebas.data.network.lotteryModels.infoModel.InfoModel
import kotlinx.serialization.json.JsonObject

interface NetworkRepo {

    suspend fun getInfo(ticket: Ticket): Result<InfoModel>

    suspend fun checkLottery(ticket: Ticket): List<Result<CheckModel>>
    //suspend fun checkLottery(ticket: Ticket): Result<CheckModel>

    suspend fun getInfoAllGames(url: String): List<JsonObject>

    suspend fun getInfoLNAC(numSorteo: String, gameId: String) : InfoLNAC




}

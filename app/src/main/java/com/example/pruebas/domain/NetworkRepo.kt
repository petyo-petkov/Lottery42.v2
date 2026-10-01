package com.example.pruebas.domain

import com.example.pruebas.data.network.InfoSorteoNacional
import com.example.pruebas.data.network.lotteryModels.checkModel.CheckModel
import com.example.pruebas.data.network.lotteryModels.infoModel.InfoModel
import com.example.pruebas.data.network.lotteryModels.infoNacional.InfoNacional

interface NetworkRepo {

    suspend fun getInfo(ticket: Ticket): Result<InfoModel>

    suspend fun getInfoSorteoNacional(numSorteo: String): InfoSorteoNacional?

    suspend fun getInfoNacional(numSorteo: String) : InfoNacional

    suspend fun checkLottery(ticket: Ticket): List<Result<CheckModel>>

}

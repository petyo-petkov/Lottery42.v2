package com.example.pruebas.domain

import com.example.pruebas.data.network.lotteryModels.checkModel.CheckModel
import com.example.pruebas.data.network.lotteryModels.infoModel.InfoModel

interface NetworkRepo {

    suspend fun getInfo(ticket: Ticket): Result<InfoModel>

    suspend fun checkLottery(ticket: Ticket): List<Result<CheckModel>>

}

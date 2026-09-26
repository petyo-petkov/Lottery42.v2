package com.example.pruebas.presentation.extraDetailScreen

import com.example.pruebas.data.network.lotteryModels.infoModel.InfoModel
import com.example.pruebas.domain.Ticket

data class ExtraDetailUiState(
    val selectedTicket: Ticket? = null,
    val infoModel: InfoModel? = null,
    val isLoadingInfo: Boolean = false,
    val error: String? = null
)

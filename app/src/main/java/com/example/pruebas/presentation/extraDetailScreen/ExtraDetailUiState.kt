package com.example.pruebas.presentation.extraDetailScreen

import com.example.pruebas.data.network.lotteryModels.infoModel.InfoModel
import com.example.pruebas.data.network.lotteryModels.infoNacional.InfoNacional
import com.example.pruebas.domain.Ticket
import com.example.pruebas.presentation.homeScreen.TicketUiModel

data class ExtraDetailUiState(
    val selectedTicket: Ticket? = null,
    val infoModel: InfoModel? = null,
    val infoNacional: InfoNacional? = null,
    val ticketUiModel: TicketUiModel? = null,
    val isLoadingInfo: Boolean = false,
    val error: String? = null
)

package com.example.pruebas.presentation.homeScreen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.pruebas.data.network.lotteryModels.checkModel.CheckModel
import com.example.pruebas.domain.Ticket
import kotlinx.serialization.json.JsonObject

data class TicketUiModel(
    val ticket: Ticket,
    val height: Dp,
    val lotteryColor: Color,
    val formattedPrize: String,
)

data class HomeUiState(
    val tickets: List<TicketUiModel> = emptyList(),
    val selectedTicket: Ticket? = null,
    val showDeleteDialog: Boolean = false,
    val deleteDialogMode: DeleteDialogMode = DeleteDialogMode.DELETE_ALL,
    val isScanning: Boolean = false,
    val checkModel: CheckModel? = null
)

package com.example.pruebas.presentation.homeScreen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.pruebas.data.network.lotteryModels.checkModel.CheckModel
import com.example.pruebas.data.network.lotteryModels.infoModel.InfoModel
import com.example.pruebas.domain.Ticket

data class TicketUiModel(
    val ticket: Ticket,
    val height: Dp,
    val lotteryColor: Color,
    val formattedPrize: String,
)

data class HomeUiState(
    val tickets: List<TicketUiModel> = emptyList(),
    val selectedTicketId: String? = null,
    val showDeleteDialog: Boolean = false,
    val deleteDialogMode: DeleteDialogMode = DeleteDialogMode.DELETE_ALL,
    val isScanning: Boolean = false,
    val isLoadingInfo: Boolean = false,
    val checkModel: CheckModel? = null,
    val infoModel: InfoModel? = null
) {
    val selectedTicketUiModel: TicketUiModel?
        get() = tickets.find { it.ticket.id == selectedTicketId }

    val selectedTicket: Ticket?
        get() = selectedTicketUiModel?.ticket
}

package com.example.pruebas.presentation.homeScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebas.data.toMoneyFormat
import com.example.pruebas.domain.LotteryDatabaseRepo
import com.example.pruebas.domain.NetworkRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val netRepo: NetworkRepo,
    private val dbRepo: LotteryDatabaseRepo
) : ViewModel() {

    var state by mutableStateOf(HomeUiState())
        private set

    init {
        viewModelScope.launch {
            dbRepo.getAllTickets().collect { tickets ->
                state = state.copy(tickets = tickets.map { it.toUiModel() })
            }
        }
    }

     fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.SelectTicket -> {
                state = state.copy(selectedTicket = intent.ticket)
            }

            is HomeIntent.DeleteTicket -> {
                deleteTicket(intent.ticket)
            }

            HomeIntent.DeleteAll -> {
                deleteAll()
            }

            is HomeIntent.ToggleDeleteDialog -> {
                state = state.copy(
                    showDeleteDialog = !state.showDeleteDialog,
                    deleteDialogMode = intent.mode ?: state.deleteDialogMode
                )
            }

            HomeIntent.Scann -> {
                state = state.copy(isScanning = !state.isScanning)
            }

            is HomeIntent.CheckTicket -> {
                checkTicket(intent.ticket)
            }
        }
    }

    private fun checkTicket(ticket: com.example.pruebas.domain.Ticket) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = netRepo.checkLottery(
                game = ticket.gameType,
                numbers = ticket.bets,
                drawId = ticket.drawId
            )
            Log.d("HomeScreenViewModel", "checkTicket: $result")
            state = state.copy(checkModel = result)

        }

    }

    private fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteAll()
        }
    }

    private fun deleteTicket(ticket: com.example.pruebas.domain.Ticket) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteTicket(ticket)
        }
    }

    private fun com.example.pruebas.domain.Ticket.toUiModel(): TicketUiModel {
        val prizeValue = prize.toDoubleOrNull() ?: 0.0
        return TicketUiModel(
            ticket = this,
            height = calculateHeight(prizeValue),
            lotteryColor = getLotteryColor(gameType),
            formattedPrize = prizeValue.toMoneyFormat()
        )
    }

    private fun calculateHeight(prize: Double): Dp {
        return when {
            prize > 800000.0 -> 200.dp
            prize > 500000.0 -> 180.dp
            prize > 30000.0 -> 160.dp
            prize > 15000.0 -> 140.dp
            prize > 1.0 -> 120.dp
            else -> 100.dp
        }
    }

    private fun getLotteryColor(gameType: String): Color {
        return when (gameType) {
            "bonoloto" -> Color(color = 0xFF98A065)
            "primitiva" -> Color(color = 0xFF43A047)
            "euromillones" -> Color(color = 0xFF283593)
            "nacional" -> Color(color = 0xFF0277BD)
            "gordo" -> Color(color = 0xFFC0392B)
            "eurodreams" -> Color(color = 0xFF8E24AA)
            else -> Color.Black
        }
    }
}

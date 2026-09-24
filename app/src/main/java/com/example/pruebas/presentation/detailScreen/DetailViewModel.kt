package com.example.pruebas.presentation.detailScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebas.domain.LotteryDatabaseRepo
import com.example.pruebas.domain.NetworkRepo
import com.example.pruebas.presentation.homeScreen.TicketUiMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val netRepo: NetworkRepo,
    private val dbRepo: LotteryDatabaseRepo
) : ViewModel() {

    var state by mutableStateOf(DetailUiState())
        private set

    fun onIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.LoadTicket -> loadTicket(intent.ticketId)
            is DetailIntent.CheckTicket -> checkTicket()
            is DetailIntent.DeleteTicket -> deleteTicket()
            is DetailIntent.ToggleDeleteDialog -> {
                state = state.copy(showDeleteDialog = !state.showDeleteDialog)
            }
            is DetailIntent.ToggleCheckDialog -> {
                state = state.copy(showCheckDialog = !state.showCheckDialog)
            }
        }
    }

    private fun loadTicket(ticketId: String) {
        viewModelScope.launch {
            dbRepo.getAllTickets().collect { tickets ->
                val ticket = tickets.find { it.id == ticketId }
                if (ticket != null) {
                    state = state.copy(ticketUiModel = TicketUiMapper.toUiModel(ticket))
                }
            }
        }
    }

    private fun checkTicket() {
        val ticket = state.ticketUiModel?.ticket ?: return
        state = state.copy(isLoadingCheck = true, showCheckDialog = true)
        viewModelScope.launch(Dispatchers.IO) {
            var totalPrize = 0.0
            val results = netRepo.checkLottery(ticket)
            results.forEach { result ->
                result.onSuccess { checkModel ->
                    Log.d("DetailViewModel", "checkTicket success: $checkModel")
                    val amount = checkModel.data?.prize?.prizeAmount?.toDoubleOrNull() ?: 0.0
                    totalPrize += amount / 100

                    state = state.copy(checkModel = checkModel, isLoadingCheck = false)
                }.onFailure { error ->
                    state = state.copy(isLoadingCheck = false, error = error.message)
                    Log.e("DetailViewModel", "checkTicket failure", error)
                }
            }
            val updatedTicket = ticket.copy(prize = totalPrize.toString(), isChecked = true)
            dbRepo.updateTicket(updatedTicket)
            state = state.copy(ticketUiModel = TicketUiMapper.toUiModel(updatedTicket))
        }
    }

    private fun deleteTicket() {
        val ticket = state.ticketUiModel?.ticket ?: return
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteTicket(ticket)
        }
    }
}

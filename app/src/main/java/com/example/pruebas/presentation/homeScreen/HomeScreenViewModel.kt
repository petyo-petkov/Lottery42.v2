package com.example.pruebas.presentation.homeScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebas.domain.BalanceRepo
import com.example.pruebas.domain.LotteryDatabaseRepo
import com.example.pruebas.domain.NetworkRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val netRepo: NetworkRepo,
    private val dbRepo: LotteryDatabaseRepo,
    private val balanceRepo: BalanceRepo
) : ViewModel() {

    var state by mutableStateOf(HomeUiState())
        private set


    init {
        viewModelScope.launch {
            dbRepo.getAllTickets().collect { tickets ->
                state = state.copy(tickets = tickets.map { TicketUiMapper.toUiModel(it) })
            }
        }
        getBalance()
    }


    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.SelectTicket -> {
                state = state.copy(selectedTicketId = intent.ticket.id)
            }

            is HomeIntent.DeleteTicket -> {
                deleteTicket(intent.ticket)
            }

            is HomeIntent.DeleteAll -> {
                deleteAll()
            }

            is HomeIntent.ToggleDeleteDialog -> {
                state = state.copy(
                    showDeleteDialog = !state.showDeleteDialog,
                    deleteDialogMode = intent.mode ?: state.deleteDialogMode
                )
            }

            is HomeIntent.Scann -> {
                state = state.copy(isScanning = !state.isScanning)
            }

            is HomeIntent.CheckTicket -> {
                checkTicket(intent.ticket)
            }

            is HomeIntent.CheckInfo -> {
                checkInfo(intent.ticket)
            }
        }
    }

    private fun checkTicket(ticket: com.example.pruebas.domain.Ticket) {
        viewModelScope.launch(Dispatchers.IO) {
            var totalPrize = 0.0
            val results = netRepo.checkLottery(ticket)
            results.forEach { result ->
                result.onSuccess { checkModel ->
                    Log.d("HomeScreenViewModel", "checkTicket success: $checkModel")
                    val amount = checkModel.data?.prize?.prizeAmount?.toDoubleOrNull() ?: 0.0
                    totalPrize += amount

                    state = state.copy(checkModel = checkModel)

                }.onFailure { error ->
                    Log.e("HomeScreenViewModel", "checkTicket failure", error)
                }
            }
            dbRepo.updateTicket(ticket.copy(prize = totalPrize.toString(), isChecked = true))
        }
    }

    private fun checkInfo(ticket: com.example.pruebas.domain.Ticket) {
        state = state.copy(isLoadingInfo = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = netRepo.getInfo(ticket)
            result.onSuccess { infoModel ->
                state = state.copy(infoModel = infoModel, isLoadingInfo = false)
                Log.d("HomeScreenViewModel", "checkTicket success: $infoModel")
            }
                .onFailure { error ->
                    state = state.copy(isLoadingInfo = false)
                    Log.e("HomeScreenViewModel", "checkTicket failure", error)
                }

        }
    }

    private fun getBalance() {
        viewModelScope.launch {
            val tickets = dbRepo.getAllTickets()
            balanceRepo.getBalance(tickets).collect { newBalance ->
                state = state.copy(balance = newBalance)
            }
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
}

package com.example.pruebas.presentation.extraDetailScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebas.domain.LotteryDatabaseRepo
import com.example.pruebas.domain.NetworkRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ExtraDetailViewModel(
    private val netRepo: NetworkRepo,
    private val dbRepo: LotteryDatabaseRepo
) : ViewModel() {

    var state by mutableStateOf(ExtraDetailUiState())
        private set

    fun onIntent(intent: ExtraDetailIntent) {
        when (intent) {
            is ExtraDetailIntent.LoadInfo -> loadInfo(intent.ticketId)
        }
    }

    private fun loadInfo(ticketId: String) {
        if (state.selectedTicket?.id == ticketId && state.infoModel != null) return

        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoadingInfo = true, error = null)
            val tickets = dbRepo.getAllTickets().firstOrNull() ?: emptyList()
            val ticket = tickets.find { it.id == ticketId }
            if (ticket != null) {
                state = state.copy(selectedTicket = ticket)
                val result = netRepo.getInfo(ticket)
                result.onSuccess { infoModel ->
                    state = state.copy(infoModel = infoModel, isLoadingInfo = false)
                    Log.d("ExtraDetailViewModel", "loadInfo success: $infoModel")
                }.onFailure { error ->
                    state = state.copy(isLoadingInfo = false, error = error.message)
                    Log.e("ExtraDetailViewModel", "loadInfo failure", error)
                }
            } else {
                state = state.copy(isLoadingInfo = false, error = "Boleto no encontrado")
            }
        }
    }
}

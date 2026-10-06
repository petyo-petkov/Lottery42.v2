package com.example.pruebas.presentation.detailScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebas.data.isDrawCelebrated
import com.example.pruebas.domain.LotteryDatabaseRepo
import com.example.pruebas.domain.LotteryGame
import com.example.pruebas.domain.WebViewRepo
import com.example.pruebas.presentation.homeScreen.TicketUiMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    ticketId: String,
    private val webViewRepo: WebViewRepo,
    private val dbRepo: LotteryDatabaseRepo
) : ViewModel() {

    var state by mutableStateOf(DetailUiState())
        private set

    private var loadTicketJob: Job? = null

    init {
        loadTicket(ticketId)
    }

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
        if (state.ticketUiModel?.ticket?.id == ticketId && loadTicketJob?.isActive == true) {
            return
        }
        loadTicketJob?.cancel()
        state = DetailUiState()

        loadTicketJob = viewModelScope.launch {
            dbRepo.getTicketById(ticketId).collect { ticket ->
                state = state.copy(ticketUiModel = TicketUiMapper.toUiModel(ticket))
            }
        }
    }

    private fun checkTicket() {
        val ticket = state.ticketUiModel?.ticket ?: return

        if (!isDrawCelebrated(ticket)) {
            state = state.copy(
                isLoadingCheck = false,
                showCheckDialog = true,
                error = "Sorteo no celebrado"
            )
            return
        }

        // 1. Mostrar estado de carga en el hilo principal
        state = state.copy(isLoadingCheck = true, showCheckDialog = true, error = null)

        viewModelScope.launch {
            var totalPrize = 0.0
            var hasError = false
            var errorMessage: String? = null

            // 2. Ejecutar tareas pesadas (Red / DB) en Dispatchers.IO
            withContext(Dispatchers.IO) {

                // A) Si es Lotería Nacional (LNAC), obtener su premio
                if ((ticket.gameId == "LNAC" || ticket.lotteryGame is LotteryGame.Nacional) && !ticket.numDecimo.isNullOrEmpty()) {
                    try {
                        val resultLNAC = webViewRepo.getExtraInfoLNAC(ticket)
                        if (resultLNAC != null && !isDrawCelebrated(ticket, resultLNAC.cierre, resultLNAC.estado)) {
                            hasError = true
                            errorMessage = "Sorteo no celebrado"
                        } else {
                            val premioCentimosStr = webViewRepo.getPremioLNAC(
                                numDecimo = ticket.numDecimo,
                                idSorteo = ticket.idSorteo
                            )
                            val cents = premioCentimosStr.toDoubleOrNull() ?: 0.0
                            totalPrize += cents / 100.0
                        }
                    } catch (e: Exception) {
                        hasError = true
                        Log.e("DetailViewModel", "Error en LNAC check", e)
                    }
                } else {
                    try {
                        val premioSentimos = webViewRepo.getPremios(ticket)
                        val centimos = premioSentimos.toDoubleOrNull() ?: 0.0
                        totalPrize += centimos.div(100)
                    } catch (e: Exception) {
                        hasError = true
                        Log.e("DetailViewModel", "Error premio check", e)
                    }

                }
                // C) Guardar en BD solo si la comprobación fue exitosa
                if (!hasError) {
                    val updatedTicket = ticket.copy(
                        prize = totalPrize.toString(),
                        isChecked = true
                    )
                    dbRepo.updateTicket(updatedTicket)
                }
            }

            // 3. Actualizar la UI en el Hilo Principal al finalizar
            state = state.copy(
                isLoadingCheck = false,
                error = if (hasError) errorMessage else null
            )
        }
    }

    private fun deleteTicket() {
        val ticket = state.ticketUiModel?.ticket ?: return
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteTicket(ticket)
        }
    }
}

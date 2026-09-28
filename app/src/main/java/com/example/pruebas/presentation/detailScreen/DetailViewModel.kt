package com.example.pruebas.presentation.detailScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebas.data.network.lotteryModels.checkModel.CheckModel
import com.example.pruebas.domain.LotteryDatabaseRepo
import com.example.pruebas.domain.NetworkRepo
import com.example.pruebas.domain.WebViewRepo
import com.example.pruebas.presentation.homeScreen.TicketUiMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val netRepo: NetworkRepo,
    private val webViewRepo: WebViewRepo,
    private val dbRepo: LotteryDatabaseRepo
) : ViewModel() {

    var state by mutableStateOf(DetailUiState())
        private set

    private var loadTicketJob: Job? = null

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

        // 1. Mostrar estado de carga en el hilo principal
        state = state.copy(isLoadingCheck = true, showCheckDialog = true, error = null)

        viewModelScope.launch {
            var totalPrize = 0.0
            var hasError = false
            var lastErrorMessage: String? = null
            var lastCheckModel: CheckModel? = null

            // 2. Ejecutar tareas pesadas (Red / DB) en Dispatchers.IO
            withContext(Dispatchers.IO) {
                // A) Si es Lotería Nacional (LNAC), obtener su premio
                if (ticket.gameType == "nacional" && !ticket.numDecimo.isNullOrEmpty()) {
                    try {
                        val premioCentimosStr = webViewRepo.getPremioLNAC(
                            numDecimo = ticket.numDecimo,
                            idSorteo = ticket.drawId
                        )
                        val cents = premioCentimosStr.toDoubleOrNull() ?: 0.0
                        totalPrize += cents / 100.0
                    } catch (e: Exception) {
                        Log.e("DetailViewModel", "Error en LNAC check", e)
                    }
                }

                // B) Comprobar combinaciones vía API
                val results = netRepo.checkLottery(ticket)
                results.forEach { result ->
                    result.onSuccess { checkModel ->
                        Log.d("DetailViewModel", "checkTicket success: $checkModel")
                        val amount = checkModel.data?.prize?.prizeAmount?.toDoubleOrNull() ?: 0.0
                        totalPrize += amount / 100.0
                        lastCheckModel = checkModel
                    }.onFailure { error ->
                        hasError = true
                        lastErrorMessage = error.message
                        Log.e("DetailViewModel", "checkTicket failure", error)
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
                checkModel = lastCheckModel,
                error = if (hasError) lastErrorMessage else null
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

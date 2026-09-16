package com.example.pruebas.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebas.data.createTicket
import com.example.pruebas.domain.LotteryDatabaseRepo
import com.example.pruebas.domain.ScannerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val scannerRepo: ScannerRepo,
    private val dbRepo: LotteryDatabaseRepo
) : ViewModel() {

    var state by mutableStateOf(ScannerUiState())
        private set

    fun onIntent(intent: ScannerIntent) {
        when (intent) {
            ScannerIntent.StartScan -> startScanning()
        }
    }

    private fun startScanning() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isScanning = true, error = null)
            scannerRepo.startScann().collect { data ->
                if (!data.isNullOrBlank()) {
                    Log.d("startScanning", data)
                    try {
                        val ticket = createTicket(data)
                        dbRepo.createTicket(ticket)
                        Log.d("startScanning", "Ticket guardado: $ticket")
                        state = state.copy(isScanning = false)
                    } catch (e: Exception) {
                        Log.e("startScanning", "Error procesando boleto", e)
                        state = state.copy(isScanning = false, error = "Error al procesar el boleto")
                    }
                } else {
                    state = state.copy(isScanning = false)
                }
            }
        }
    }
}

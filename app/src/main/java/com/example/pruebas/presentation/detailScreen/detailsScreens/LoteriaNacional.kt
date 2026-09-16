package com.example.pruebas.presentation.detailScreen.detailsScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pruebas.domain.Ticket
import com.example.pruebas.presentation.detailScreen.Divisor
import com.example.pruebas.presentation.detailScreen.Info

@Composable
fun LoteriaNacional(ticket: Ticket) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Info(text = "Número: ${ticket.numLottery}")
        Divisor()
        Info(text = "Serie: ${ticket.serie}")
        Divisor()
        Info(text = "Fracción: ${ticket.fraccion}")
    }
}
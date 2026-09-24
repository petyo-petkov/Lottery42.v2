package com.example.pruebas.presentation.detailScreen.detailsScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pruebas.domain.Ticket
import com.example.pruebas.presentation.Divisor
import com.example.pruebas.presentation.InfoText

@Composable
fun LoteriaNacional(ticket: Ticket) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InfoText(text = "Número: ${ticket.numDecimo}")
        Divisor()
        InfoText(text = "Serie: ${ticket.serie}")
        Divisor()
        InfoText(text = "Fracción: ${ticket.fraccion}")
    }
}
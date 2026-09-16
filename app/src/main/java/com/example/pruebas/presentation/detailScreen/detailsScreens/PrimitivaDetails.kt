package com.example.pruebas.presentation.detailScreen.detailsScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pruebas.domain.Ticket
import com.example.pruebas.presentation.detailScreen.Divisor
import com.example.pruebas.presentation.detailScreen.Info

@Composable
fun PrimitivaDetails(ticket: Ticket) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NumberRow(bets = ticket.numbers)
        Divisor()
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Reintegro:")
            NumberCircle(
                number = ticket.extraNumbers?.first().toString(),
                color = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Divisor()
        Info(text = "Joker: ${ticket.joker}")
    }
}


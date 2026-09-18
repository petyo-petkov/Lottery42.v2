package com.example.pruebas.presentation.homeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.pruebas.domain.Ticket

@Composable
fun TicketUI(
    uiModel: TicketUiModel,
    onClick: (Ticket) -> Unit
) {
    val ticket = uiModel.ticket

    OutlinedCard(
        onClick = { onClick(ticket) },
        modifier = Modifier
            .fillMaxWidth()
            .height(uiModel.height),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        border = BorderStroke(color = uiModel.lotteryColor, width = 1.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Date
            Fila(
                text = ticket.drawDate,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            // Game
            Fila(
                text = ticket.name,
                style = MaterialTheme.typography.headlineSmallEmphasized,
                color = uiModel.lotteryColor
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Price
                Fila(
                    text = "${ticket.betPrice} €",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                //Prize
                Fila(
                    //text = "${uiModel.formattedPrize} €",
                    text = "${ticket.prize} €",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

    }
}

@Composable
fun Fila(text: String, style: TextStyle, color: Color) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = style,
            color = color
        )
    }
}
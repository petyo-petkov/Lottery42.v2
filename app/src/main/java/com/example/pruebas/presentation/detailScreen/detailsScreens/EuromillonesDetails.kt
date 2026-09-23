package com.example.pruebas.presentation.detailScreen.detailsScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pruebas.domain.Ticket
import com.example.pruebas.presentation.Divisor
import com.example.pruebas.presentation.InfoText

@Composable
fun EuromillonesDetails(
    ticket: Ticket
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        val stars = ticket.stars ?: emptyList()
        NumberRow(
            bets = ticket.numbers,
            contentAfterNumbers = { index ->
                if (index < stars.size) {
                    val starsInBet = stars[index].split(",")
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        starsInBet.forEach { star ->
                            NumberCircle(
                                number = star,
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                textColor = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                }
            }
        )
        Divisor()
        InfoText(text = "Millon: ${ticket.millon}")
    }
}
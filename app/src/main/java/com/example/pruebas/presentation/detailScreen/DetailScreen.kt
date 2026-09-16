package com.example.pruebas.presentation.detailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.pruebas.data.toMoneyFormat
import com.example.pruebas.domain.Ticket
import com.example.pruebas.presentation.detailScreen.detailsScreens.BonolotoDetails
import com.example.pruebas.presentation.detailScreen.detailsScreens.EurodreamsDetails
import com.example.pruebas.presentation.detailScreen.detailsScreens.EuromillonesDetails
import com.example.pruebas.presentation.detailScreen.detailsScreens.Gordo
import com.example.pruebas.presentation.detailScreen.detailsScreens.LoteriaNacional
import com.example.pruebas.presentation.detailScreen.detailsScreens.PrimitivaDetails


@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    ticket: Ticket,
    onDelete: () -> Unit,
    onCheck: () -> Unit
) {
    val buttonTexts = listOf("Borrar", "Comprobar", "Info")
    val buttonIcons =
        listOf(Icons.Outlined.Delete, Icons.Outlined.Check, Icons.Outlined.Info)
    var selectedItemIndex by remember { mutableIntStateOf(1) }

    OutlinedCard(
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Info(
                    text = ticket.name,
                    style = MaterialTheme.typography.displayMedium
                )
                Divisor()
                Info(text = ticket.drawDate)
                Divisor()
                Info(text = "Id: ${ticket.drawId}")
                Divisor()
                Info(text = "Sorteo: ${ticket.cdc}")
                Divisor()
                if (ticket.gameType != "nacional")
                    Info(text = "Apuestas:")
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (ticket.gameType) {
                        "euromillones" -> EuromillonesDetails(ticket)
                        "primitiva" -> PrimitivaDetails(ticket)
                        "eurodreams" -> EurodreamsDetails(ticket)
                        "bonoloto" -> BonolotoDetails(ticket)
                        "nacional" -> LoteriaNacional(ticket)
                        "gordo" -> { Gordo(ticket) }
                    }
                }
            }
            item {
                Divisor()
                Info(text = "\uD83C\uDFC6 ${(ticket.prize.toDoubleOrNull() ?: 0.0).toMoneyFormat()} €")
            }

            item {
                ButtonGroup(
                    modifier = Modifier
                        .safeDrawingPadding()
                        .fillMaxWidth(),
                    overflowIndicator = {}
                ) {
                    buttonTexts.forEachIndexed { index, label ->
                        clickableItem(
                            onClick = {
                                if (index == 0) onDelete()
                                if (index == 1) onCheck()
                                selectedItemIndex = index
                            },
                            label = label,
                            icon = {
                                Icon(imageVector = buttonIcons[index], contentDescription = "")
                            },

                            )

                    }
                }
            }

        }

    }

}

@Composable
fun Info(
    text: String,
    style: TextStyle = MaterialTheme.typography.titleLarge
) {
    Text(
        text = text,
        modifier = Modifier.padding(6.dp),
        color = MaterialTheme.colorScheme.onSurface,
        style = style
    )
}


@Composable
fun Divisor() {
    HorizontalDivider(
        modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
        color = MaterialTheme.colorScheme.tertiary
    )
}
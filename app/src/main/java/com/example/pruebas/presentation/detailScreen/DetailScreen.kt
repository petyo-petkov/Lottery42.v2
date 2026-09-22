package com.example.pruebas.presentation.detailScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pruebas.data.network.lotteryModels.checkModel.CheckModel
import com.example.pruebas.data.toDisplayDate
import com.example.pruebas.presentation.Divisor
import com.example.pruebas.presentation.Info
import com.example.pruebas.presentation.detailScreen.detailsScreens.BonolotoDetails
import com.example.pruebas.presentation.detailScreen.detailsScreens.EurodreamsDetails
import com.example.pruebas.presentation.detailScreen.detailsScreens.EuromillonesDetails
import com.example.pruebas.presentation.detailScreen.detailsScreens.Gordo
import com.example.pruebas.presentation.detailScreen.detailsScreens.LoteriaNacional
import com.example.pruebas.presentation.detailScreen.detailsScreens.PrimitivaDetails
import com.example.pruebas.presentation.homeScreen.HomeUiState
import com.example.pruebas.presentation.homeScreen.TicketUiModel


@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    ticketUiModel: TicketUiModel,
    checkModel: CheckModel?,
    onDelete: () -> Unit,
    onCheck: () -> Unit,
    onInfo: () -> Unit
) {
    val buttonTexts = listOf("Borrar", "Comprobar", "Info")
    val buttonIcons =
        listOf(Icons.Outlined.Delete, Icons.Outlined.Check, Icons.Outlined.Info)
    var selectedItemIndex by remember { mutableIntStateOf(1) }

    val ticket = ticketUiModel.ticket
    val lotteryColor = Color(ticketUiModel.lotteryColorHex)

    var showDialog by remember() { mutableStateOf(false) }

    OutlinedCard(
        modifier = modifier
            .padding(6.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        border = BorderStroke(color = lotteryColor, width = 1.dp)
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
                    style = MaterialTheme.typography.displayMedium,
                    color = lotteryColor
                )
                Divisor()
                Info(text = ticket.drawDate.toDisplayDate())
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
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically
                    ) {
                        Info(
                            text = "\uD83C\uDFC6 ${(ticket.prize.toDoubleOrNull() ?: 0.0)} €"
                        )
                        if (ticket.isChecked){
                        Icon(Icons.Filled.Check, contentDescription = null, tint = Color.Green)
                    }
                }

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
                                if (index == 1) {
                                    onCheck()
                                    showDialog = true
                                }
                                if (index == 2) onInfo()
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

    if (showDialog && checkModel?.data != null) {
        InfoDialog(
            onDismiss = { showDialog = false },
            showDialog = true,
            checkdata = checkModel
        )
    }
}


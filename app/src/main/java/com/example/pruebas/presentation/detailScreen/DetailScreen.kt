package com.example.pruebas.presentation.detailScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LoadingIndicatorDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pruebas.data.toDisplayDate
import com.example.pruebas.presentation.Divisor
import com.example.pruebas.presentation.InfoText
import com.example.pruebas.presentation.detailScreen.detailsScreens.BonolotoDetails
import com.example.pruebas.presentation.detailScreen.detailsScreens.EurodreamsDetails
import com.example.pruebas.presentation.detailScreen.detailsScreens.EuromillonesDetails
import com.example.pruebas.presentation.detailScreen.detailsScreens.Gordo
import com.example.pruebas.presentation.detailScreen.detailsScreens.LoteriaNacional
import com.example.pruebas.presentation.detailScreen.detailsScreens.PrimitivaDetails

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    state: DetailUiState,
    onIntent: (DetailIntent) -> Unit,
    onDelete: () -> Unit,
    onInfo: () -> Unit
) {
    val ticketUiModel = state.ticketUiModel
    if (ticketUiModel == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LoadingIndicator(
                color = LoadingIndicatorDefaults.indicatorColor,
                polygons = LoadingIndicatorDefaults.IndeterminateIndicatorPolygons
            )
        }
        return
    }

    val buttonTexts = listOf("Borrar", "Comprobar", "Info")
    val buttonIcons = listOf(Icons.Outlined.Delete, Icons.Outlined.Check, Icons.Outlined.Info)
    var selectedItemIndex by remember { mutableIntStateOf(1) }

    val ticket = ticketUiModel.ticket
    val lotteryColor = Color(ticketUiModel.lotteryColorHex)

    OutlinedCard(
        modifier = modifier.padding(6.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        border = BorderStroke(color = lotteryColor, width = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            // Tipo Loteria
            InfoText(
                text = ticket.name,
                style = MaterialTheme.typography.displayMedium,
                color = lotteryColor
            )
            Divisor()

            // Fecha
            InfoText(text = ticket.drawDate.toDisplayDate())
            Divisor()

            // #Sorteo
            InfoText(text = "Sorteo: ${ticket.numeroSorteo}")
            Divisor()
            if (ticket.gameType != "nacional")
                InfoText(text = "Apuestas:")

            // Extra Info
            when (ticket.gameType) {
                "euromillones" -> EuromillonesDetails(ticket)
                "primitiva" -> PrimitivaDetails(ticket)
                "eurodreams" -> EurodreamsDetails(ticket)
                "bonoloto" -> BonolotoDetails(ticket)
                "nacional" -> LoteriaNacional(ticket)
                "gordo" -> {
                    Gordo(ticket)
                }
            }
            Divisor()

            // Premio
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InfoText(
                    text = "\uD83C\uDFC6  ${(ticket.prize.toDoubleOrNull() ?: 0.0)} €"
                )
                if (ticket.isChecked) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = null,
                        tint = Color(0xFF43A047)
                    )
                }
            }
            Divisor()

            // Botones
            ButtonGroup(
                modifier = Modifier.padding(top = 12.dp),
                overflowIndicator = {}
            ) {
                buttonTexts.forEachIndexed { index, label ->
                    clickableItem(
                        onClick = {
                            if (index == 0) onDelete()
                            if (index == 1) {
                                onIntent(DetailIntent.CheckTicket)
                            }
                            if (index == 2) onInfo()
                            selectedItemIndex = index
                        },
                        label = label,
                        icon = {
                            Icon(imageVector = buttonIcons[index], contentDescription = "")
                        }
                    )
                }
            }
        }
    }

    if (state.showCheckDialog) {
        InfoDialog(
            onDismiss = { onIntent(DetailIntent.ToggleCheckDialog) },
            showDialog = true,
            isLoadingCheck = state.isLoadingCheck,
            checkdata = state.checkModel
        )
    }
}

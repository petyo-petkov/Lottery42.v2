package com.example.pruebas.presentation.extraDetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LoadingIndicatorDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
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
import com.example.pruebas.presentation.detailScreen.detailsScreens.NumberCircle
import com.example.pruebas.presentation.detailScreen.detailsScreens.PrimitivaDetails

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExtraDetailScreen(
    modifier: Modifier = Modifier,
    state: ExtraDetailUiState
) {
    val selectedTicket = state.selectedTicket
    val infoModel = state.infoModel
    val isLoading = state.isLoadingInfo

    val info = infoModel?.data?.getOrNull(0)

    OutlinedCard(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),

        ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingIndicator(
                    modifier = Modifier,
                    color = LoadingIndicatorDefaults.indicatorColor,
                    polygons = LoadingIndicatorDefaults.IndeterminateIndicatorPolygons
                )
            }
        } else {
            when (state.selectedTicket?.gameType) {
                "nacional" -> ExtraInfoNcional(state)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                InfoText(
                    text = info?.game?.name ?: "",
                    style = MaterialTheme.typography.displaySmall,
                    color = Color(state.ticketUiModel?.lotteryColorHex ?: 1L)
                )
                Divisor()

                InfoText(text = info?.drawDate?.toDisplayDate() ?: "")
                Divisor()

                InfoText(text = "Mis Combinaciones:")
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    when (selectedTicket?.gameType) {
                        "euromillones" -> EuromillonesDetails(selectedTicket)
                        "primitiva" -> PrimitivaDetails(selectedTicket)
                        "eurodreams" -> EurodreamsDetails(selectedTicket)
                        "bonoloto" -> BonolotoDetails(selectedTicket)
                        "gordo" -> Gordo(selectedTicket)
                    }
                }
                Divisor()

                InfoText("Combinación ganadora:")
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val combination = info?.combination?.filterNotNull()?.sorted()
                    combination?.forEach { numero ->
                        NumberCircle(
                            number = numero.toString(),
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                }
                Divisor()

                if (info?.resultData?.reintegro != null) {
                    Row {
                        InfoText("Reintegro:")
                        NumberCircle(
                            number = info.resultData.reintegro.toString(),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                    }
                    Divisor()
                }
                if (info?.resultData?.complementario != null) {
                    Row {
                        InfoText("Complimentario:")
                        NumberCircle(
                            number = info.resultData.complementario.toString(),
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    }
                    Divisor()
                }

                EscrutinioSection(info?.prizes ?: emptyList())

            }

        }
    }
}
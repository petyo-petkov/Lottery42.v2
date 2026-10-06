package com.example.pruebas.presentation.extraDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pruebas.data.toDisplayDate
import com.example.pruebas.presentation.Divisor
import com.example.pruebas.presentation.InfoText
import com.example.pruebas.presentation.detailScreen.detailsScreens.NumberCircle

@Composable
fun ExtraInfoNcional(
    state: ExtraDetailUiState
) {
    // val info = state.infoNacional?.data?.lastOrNull()
    val info = state.infoNacional
    val ticket = state.selectedTicket
    val uiModel = state.ticketUiModel

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surfaceContainerLow),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {

        InfoText(
            text = "Loteria Nacional",
            style = MaterialTheme.typography.displaySmall,
            color = Color(uiModel?.lotteryColorHex ?: 1L)
        )
        Divisor()

        InfoText(text = info?.fecha_sorteo?.toDisplayDate() ?: "")
        Divisor()

        InfoText(text = "Décimo: ${ticket?.numDecimo}")
        Divisor()

        InfoText(text = "Serie: ${ticket?.serie}")
        Divisor()

        InfoText(text = "Fracción: ${ticket?.fraccion}")
        Divisor()

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            //Primer Premio
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                InfoText("Primer premio")
                InfoText(info?.primerPremio?.decimo)
            }
            //Segundo Premio
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InfoText("Segundo premio")
                InfoText(info?.segundoPremio?.decimo)

            }
        }
        Divisor()
        if (info?.tercerosPremios?.isNotEmpty() == true ||
            info?.cuartosPremios?.isNotEmpty() == true ||
            info?.quintosPremios?.isNotEmpty() == true
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InfoText("Terceros premios:")
                    info.tercerosPremios.forEach { premio ->
                        if (premio.decimo.isNotEmpty())
                            InfoText(premio.decimo)
                    }
                }

            }
            Divisor()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InfoText("Cuartos premios:")
                    info.cuartosPremios.forEach { premio ->
                        InfoText(premio.decimo)
                    }

                }
            }
            Divisor()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InfoText("Quintos premios:")
                    info.quintosPremios.forEach { premio ->
                        InfoText(premio.decimo)
                    }

                }
            }
            Divisor()
        }
        InfoText("Reintegros:")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            info?.reintegros?.forEach { reintegro ->
                NumberCircle(reintegro.decimo)
            }
        }
    }

}



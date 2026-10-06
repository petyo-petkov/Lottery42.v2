package com.example.pruebas.presentation.detailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LoadingIndicatorDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.pruebas.data.toCurrencyFormat
import com.example.pruebas.data.toDisplayDate
import com.example.pruebas.domain.Ticket
import com.example.pruebas.presentation.InfoText

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun InfoDialog(
    onDismiss: () -> Unit,
    showDialog: Boolean,
    isLoadingCheck: Boolean,
    ticket: Ticket,
    error: String? = null
) {
    val prize = ticket.prize
    val name = ticket.name
    val date = ticket.fecha.toDisplayDate()

    if (showDialog) {

        BasicAlertDialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            content = {
                Surface(
                    modifier = Modifier.size(width = 300.dp, height = 220.dp),
                    shape = MaterialTheme.shapes.medium,
                    tonalElevation = AlertDialogDefaults.TonalElevation,
                ) {


                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            InfoText("Boleto: $name")
                            InfoText(date)
                        }


                        if (isLoadingCheck) {

                            Box(
                                modifier = Modifier,
                                contentAlignment = Alignment.Center
                            ) {
                                LoadingIndicator(
                                    modifier = Modifier,
                                    color = LoadingIndicatorDefaults.containedIndicatorColor,
                                    polygons = LoadingIndicatorDefaults.IndeterminateIndicatorPolygons
                                )
                            }
                        } else {

                            if (error != null) {
                                InfoText(error)
                            } else if (prize != "0.0") {
                                InfoText("Has Ganado: ${prize.toCurrencyFormat()}")
                            } else {
                                InfoText("No Premiado")
                            }
                        }

                        ElevatedButton(
                            onClick = { onDismiss() },
                            modifier = Modifier,
                            shape = ButtonDefaults.elevatedShape,
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        ) {
                            Text(
                                text = "Ok",
                                modifier = Modifier,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                fontSize = 18.sp
                            )
                        }

                    }
                }


            }
        )

    }

}



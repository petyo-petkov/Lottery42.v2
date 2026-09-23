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
import com.example.pruebas.data.network.lotteryModels.checkModel.CheckModel
import com.example.pruebas.presentation.InfoText

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun InfoDialog(
    onDismiss: () -> Unit,
    showDialog: Boolean,
    isLoadingCheck: Boolean,
    checkdata: CheckModel?
) {
    val prize = checkdata?.data?.prize?.formattedPrize

    if (showDialog) {

        BasicAlertDialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            content = {
                Surface(
                    modifier = Modifier.size(width = 300.dp, height = 220.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    tonalElevation = AlertDialogDefaults.TonalElevation,
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InfoText("Boleto: ${checkdata?.data?.game?.name}")

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

                            if (prize != null) {
                                InfoText("Ha Ganado: ${prize}")
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



package com.example.pruebas.presentation.extraDetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LoadingIndicatorDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pruebas.data.network.lotteryModels.infoModel.InfoLotteryPrize
import com.example.pruebas.data.network.lotteryModels.infoModel.InfoModel
import com.example.pruebas.presentation.Divisor
import com.example.pruebas.presentation.Info
import com.example.pruebas.presentation.detailScreen.detailsScreens.NumberCircle


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExtraDetailScreen(
    modifier: Modifier = Modifier,
    model: InfoModel?,
    isLoading: Boolean
) {

    val info = model?.data?.get(0)

    OutlinedCard(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),

        ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingIndicator(
                    modifier = modifier,
                    color = LoadingIndicatorDefaults.indicatorColor,
                    polygons = LoadingIndicatorDefaults.IndeterminateIndicatorPolygons
                )
            }
        } else {
            LazyColumn(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                item {
                    Info(text = info?.game?.name ?: "")
                    Divisor()
                    Info(text = info?.drawDate ?: "")
                    Divisor()
                    Info("Combinación ganadora:")
                    Row(
                        modifier = modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        info?.combination?.forEach { numero ->
                            NumberCircle(
                                number = numero.toString(),
                                color = MaterialTheme.colorScheme.primaryContainer
                            )

                        }

                    }
                    Divisor()


                    Row() {
                        Info("Reintegro:")
                        NumberCircle(
                            number = "${info?.resultData?.reintegro}",
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                    }
                    Divisor()
                    Row() {
                        Info("Complimentario:")
                        NumberCircle(
                            number = "${info?.resultData?.complementario}",
                            color = MaterialTheme.colorScheme.tertiaryContainer
                            )
                    }
                    Divisor()


                    Info("Jackpot:")
                    Info(text = info?.jackpotFormatted ?: "")
                    Divisor()

                }

                escrutinioSection(info?.prizes as List<InfoLotteryPrize>)


            }
        }
    }

}


private fun LazyListScope.escrutinioSection(prize: List<InfoLotteryPrize>) {
    item {
        Text("Escrutinio:", style = MaterialTheme.typography.headlineSmall)
    }

    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Categoria",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Ganadores",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Premio",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
    }

    items(prize) { item ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.categoryName ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = item.winners.toString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${item.formattedPrize}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
    }
}




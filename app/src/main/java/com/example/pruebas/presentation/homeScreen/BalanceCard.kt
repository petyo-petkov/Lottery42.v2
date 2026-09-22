package com.example.pruebas.presentation.homeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BalanceCard(
   balanceState: BalanceState
) {
    OutlinedCard (
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .size(130.dp)
        ,
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(top = 32.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BalanceData(
                nombre = "GASTADO",
                color = Color.Red,
                data = balanceState.gastado
            )
            BalanceData(
                nombre = "BALANCE",
                color = Color(0xFF1976D2),
                data = balanceState.balance,
                extraData = balanceState.porcentaje
            )
            BalanceData(
                nombre = "GANADO",
                color = Color(0xFF388E3C),
                data = balanceState.ganado,
            )

        }

    }
}

@Composable
fun BalanceData(
    nombre: String,
    data: String,
    color: Color,
    extraData: String? = null
) {

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = nombre,
            color = color,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = data,
            style = MaterialTheme.typography.headlineSmall

        )
        if (extraData != null) {
            Text(
                text = extraData,
                style = MaterialTheme.typography.bodyLarge,

                )
        }

    }

}
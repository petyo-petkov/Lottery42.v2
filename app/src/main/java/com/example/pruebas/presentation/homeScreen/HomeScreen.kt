package com.example.pruebas.presentation.homeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pruebas.domain.Ticket


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    tickets: List<TicketUiModel>,
    balanceState: BalanceState,
    onClick: (Ticket) -> Unit

) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        BalanceCard(balanceState)

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 6.dp,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .padding(12.dp)

        ) {
            items(tickets) { uiModel ->
                TicketUI(
                    uiModel = uiModel,
                    onClick = onClick
                )
            }
        }
    }
}

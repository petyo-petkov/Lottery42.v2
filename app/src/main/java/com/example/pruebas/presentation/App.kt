package com.example.pruebas.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.pruebas.presentation.detailScreen.DetailScreen
import com.example.pruebas.presentation.extraDetailScreen.ExtraDetailScreen
import com.example.pruebas.presentation.homeScreen.DeleteDialogMode
import com.example.pruebas.presentation.homeScreen.HomeIntent
import com.example.pruebas.presentation.homeScreen.HomeScreen
import com.example.pruebas.presentation.homeScreen.HomeScreenViewModel
import com.example.pruebas.presentation.homeScreen.MyFAB
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(
    homeVM: HomeScreenViewModel = koinViewModel(),
    scannerVM: ScannerViewModel = koinViewModel(),
) {
    val backStack = rememberNavBackStack(HomeKey)
    val state = homeVM.state

    val currentKey = backStack.lastOrNull()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AnimatedVisibility(
                visible = currentKey is HomeKey,
                enter = fadeIn(tween(durationMillis = 100)),
                exit = fadeOut(tween(durationMillis = 100))
            ) {
                MyFAB(
                    onDeleteClick = {
                        homeVM.onIntent(HomeIntent.ToggleDeleteDialog(DeleteDialogMode.DELETE_ALL))
                    },
                    onScannerClick = {
                        scannerVM.onIntent(ScannerIntent.StartScan)
                    },
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets.safeDrawing
    ) { padding ->
        val entryProvider = entryProvider {
            entry<HomeKey> {
                HomeScreen(
                    modifier = Modifier.padding(padding),
                    tickets = state.tickets,
                    balanceState = state.balance,
                    onClick = { ticket ->
                        homeVM.onIntent(HomeIntent.SelectTicket(ticket))
                        backStack.add(DetailKey(ticket.id))
                    }
                )
            }

            entry<DetailKey> { key ->
                val ticketUiModel = state.tickets.find { it.ticket.id == key.ticketId }
                ticketUiModel?.let { uiModel ->
                    DetailScreen(
                        modifier = Modifier.padding(padding),
                        ticketUiModel = uiModel,
                        checkModel = state.checkModel,
                        isLodingCheck = state.isLoadingCheck,
                        onDelete = {
                            homeVM.onIntent(HomeIntent.SelectTicket(uiModel.ticket))
                            homeVM.onIntent(HomeIntent.ToggleDeleteDialog(DeleteDialogMode.DELETE_SINGLE))
                        },
                        onCheck = {
                            homeVM.onIntent(HomeIntent.CheckTicket(uiModel.ticket))
                        },
                        onInfo = {
                            homeVM.onIntent(HomeIntent.CheckInfo(uiModel.ticket))
                            backStack.add(ExtraDetailKey(key.ticketId))
                        }
                    )
                }
            }

            entry<ExtraDetailKey> {
                ExtraDetailScreen(
                    modifier = Modifier.padding(padding),
                    model = state.infoModel,
                    selectedTicket = state.selectedTicket,
                    isLoading = state.isLoadingInfo
                )
            }

            entry<SettingsKey> {
                // Destino para pantalla de ajustes u otra pantalla futura
            }
        }

        NavDisplay(
            backStack = backStack,
            entryProvider = entryProvider,
            onBack = { backStack.removeLastOrNull() }
        )
    }

    DeleteDialog(
        onDismiss = { homeVM.onIntent(HomeIntent.ToggleDeleteDialog()) },
        onConfirm = {
            if (state.deleteDialogMode == DeleteDialogMode.DELETE_ALL) {
                homeVM.onIntent(HomeIntent.DeleteAll)
            } else {
                state.selectedTicket?.let { ticket ->
                    homeVM.onIntent(HomeIntent.DeleteTicket(ticket))
                    backStack.removeLastOrNull()
                }
            }
            homeVM.onIntent(HomeIntent.ToggleDeleteDialog())
        },
        show = state.showDeleteDialog,
        mensaje = if (state.deleteDialogMode == DeleteDialogMode.DELETE_ALL)
            "Borrar todos los Boletos?"
        else
            "Borrar este Boleto?"
    )
}

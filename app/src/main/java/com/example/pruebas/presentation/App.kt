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
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.BackNavigationBehavior
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.pruebas.presentation.detailScreen.DetailScreen
import com.example.pruebas.presentation.extraDetailScreen.ExtraDetailScreen
import com.example.pruebas.presentation.homeScreen.DeleteDialogMode
import com.example.pruebas.presentation.homeScreen.HomeIntent
import com.example.pruebas.presentation.homeScreen.HomeScreen
import com.example.pruebas.presentation.homeScreen.HomeScreenViewModel
import com.example.pruebas.presentation.homeScreen.MyFAB
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
    homeVM: HomeScreenViewModel = koinViewModel(),
    scannerVM: ScannerViewModel = koinViewModel()
) {

    val navigator = rememberListDetailPaneScaffoldNavigator()
    val coroutine = rememberCoroutineScope()

    val state = homeVM.state

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            AnimatedVisibility(
                visible = navigator.currentDestination?.pane == ThreePaneScaffoldRole.Secondary,
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
        NavigableListDetailPaneScaffold(
            modifier = modifier.padding(padding),
            navigator = navigator,
            listPane = {
                AnimatedPane {
                    HomeScreen(
                        modifier = modifier,
                        tickets = state.tickets,
                        onClick = { ticket ->
                            homeVM.onIntent(HomeIntent.SelectTicket(ticket))
                            coroutine.launch {
                                navigator.navigateTo(pane = ThreePaneScaffoldRole.Primary)
                            }
                        }
                    )
                }
            },
            detailPane = {
                AnimatedPane {
                    state.selectedTicketUiModel?.let { ticketUiModel ->
                        val currentTicket = ticketUiModel.ticket
                        DetailScreen(
                            ticketUiModel = ticketUiModel,
                            state = state,
                            onDelete = {
                                homeVM.onIntent(HomeIntent.ToggleDeleteDialog(DeleteDialogMode.DELETE_SINGLE))
                            },
                            onCheck = {
                                coroutine.launch {
                                    homeVM.onIntent(HomeIntent.CheckTicket(currentTicket))
                                }
                            },
                            onInfo = {
                                coroutine.launch {
                                    homeVM.onIntent(HomeIntent.CheckInfo(currentTicket))
                                    navigator.navigateTo(pane = ThreePaneScaffoldRole.Tertiary)
                                }
                            }
                        )
                    }
                }
            },
            extraPane = {
                AnimatedPane {
                    ExtraDetailScreen(
                        model = state.infoModel,
                        selectedTicket = state.selectedTicket,
                        isLoading = state.isLoadingInfo
                    )
                }
            },
            defaultBackBehavior = BackNavigationBehavior.PopUntilContentChange

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
                    coroutine.launch {
                        navigator.navigateBack()
                    }
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

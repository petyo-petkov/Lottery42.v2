package com.example.pruebas.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.metadata
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.pruebas.presentation.detailScreen.DetailIntent
import com.example.pruebas.presentation.detailScreen.DetailScreen
import com.example.pruebas.presentation.detailScreen.DetailViewModel
import com.example.pruebas.presentation.extraDetailScreen.ExtraDetailScreen
import com.example.pruebas.presentation.extraDetailScreen.ExtraDetailViewModel
import com.example.pruebas.presentation.homeScreen.HomeIntent
import com.example.pruebas.presentation.homeScreen.HomeScreen
import com.example.pruebas.presentation.homeScreen.HomeScreenViewModel
import com.example.pruebas.presentation.homeScreen.MyFAB
import com.example.pruebas.presentation.jackpotScreen.JackpotScreen
import com.example.pruebas.presentation.jackpotScreen.JackpotScreenViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

private val fadeTransitionMetadata = metadata {
    put(NavDisplay.TransitionKey) {
        fadeIn(animationSpec = tween()) togetherWith fadeOut(
            animationSpec = tween()
        )
    }
    put(NavDisplay.PopTransitionKey) {
        fadeIn(animationSpec = tween()) togetherWith fadeOut(
            animationSpec = tween()
        )
    }
    put(NavDisplay.PredictivePopTransitionKey) {
        fadeIn(animationSpec = tween()) togetherWith fadeOut(
            animationSpec = tween()
        )
    }
}

@Composable
fun App(
    homeVM: HomeScreenViewModel = koinViewModel(),
    scannerVM: ScannerViewModel = koinViewModel()
) {
    val state = homeVM.state

    val backStack = rememberNavBackStack(HomeKey)
    val currentKey = backStack.lastOrNull()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AnimatedVisibility(
                visible = currentKey is HomeKey,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                MyFAB(
                    onDeleteClick = {
                        homeVM.onIntent(HomeIntent.ToggleDeleteDialog)
                    },
                    onScannerClick = {
                        scannerVM.onIntent(ScannerIntent.StartScan)
                    },
                    onJackpotClick = {
                        homeVM.onIntent(HomeIntent.Check)
                        backStack.add(JackpotKey)
                    }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets.safeDrawing
    ) { padding ->
        val entryProvider = entryProvider {
            entry<HomeKey>(
                metadata = fadeTransitionMetadata
            ) {
                HomeScreen(
                    modifier = Modifier.padding(padding),
                    tickets = state.tickets,
                    balanceState = state.balance,
                    onClick = { ticket ->
                        backStack.add(DetailKey(ticket.id))
                    }
                )
            }

            entry<DetailKey>(
                metadata = fadeTransitionMetadata
            ) { key ->
                val detailVM: DetailViewModel = koinViewModel { parametersOf(key.ticketId) }
                DetailScreen(
                    modifier = Modifier.padding(padding),
                    state = detailVM.state,
                    onIntent = detailVM::onIntent,
                    onDelete = {
                        detailVM.onIntent(DetailIntent.ToggleDeleteDialog)
                    },
                    onInfo = {
                        backStack.add(ExtraDetailKey(key.ticketId))
                    }
                )
                DeleteDialog(
                    onDismiss = { detailVM.onIntent(DetailIntent.ToggleDeleteDialog) },
                    onConfirm = {
                        detailVM.onIntent(DetailIntent.DeleteTicket)
                        detailVM.onIntent(DetailIntent.ToggleDeleteDialog)
                        backStack.removeLastOrNull()
                    },
                    show = detailVM.state.showDeleteDialog,
                    mensaje = "Borrar este Boleto?"
                )
            }

            entry<ExtraDetailKey>(
                metadata = fadeTransitionMetadata
            ) { key ->
                val extraDetailVM: ExtraDetailViewModel =
                    koinViewModel { parametersOf(key.ticketId) }
                ExtraDetailScreen(
                    modifier = Modifier.padding(padding),
                    state = extraDetailVM.state
                )
            }

            entry<JackpotKey>(
                metadata = fadeTransitionMetadata
            ) {
                val jackpotScreenVM: JackpotScreenViewModel =
                    koinViewModel()
                JackpotScreen(
                    jackpotState = jackpotScreenVM.jackpotUiState,
                    modifier = Modifier.padding(padding)
                )
            }
        }

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider
        )
    }

    DeleteDialog(
        onDismiss = { homeVM.onIntent(HomeIntent.ToggleDeleteDialog) },
        onConfirm = {
            homeVM.onIntent(HomeIntent.DeleteAll)
            homeVM.onIntent(HomeIntent.ToggleDeleteDialog)
        },
        show = state.showDeleteDialog,
        mensaje = "Borrar todos los Boletos?"
    )
}
package com.example.pruebas.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DeleteDialog (
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    show: Boolean,
    mensaje: String
) {
    if (show) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(
                    onClick = { onConfirm() },
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.error)
                ) {
                    Text(
                        text = "Borrar",
                        color = MaterialTheme.colorScheme.onError,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            },
            modifier = Modifier.padding(12.dp),
            dismissButton = {
                TextButton(
                    onClick = { onDismiss() },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "Cancelar",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            },
            title = {
                Text(
                    text = mensaje,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.headlineMedium
                )
            },
            containerColor = MaterialTheme.colorScheme.surfaceBright,
        )
    }

}
package com.example.pruebas.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


@Composable
fun InfoText(
    text: String?,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    Text(
        text = text ?: "",
        modifier = Modifier.padding(6.dp),
        color = color,
        style = style
    )
}


@Composable
fun Divisor() {
    HorizontalDivider(
        modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
        color = MaterialTheme.colorScheme.tertiary
    )
}
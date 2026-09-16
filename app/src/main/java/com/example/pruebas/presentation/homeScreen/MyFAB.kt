import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons as MaterialIcons


@Composable
fun MyFAB(
    onDeleteClick: () -> Unit,
    onScannerClick: () -> Unit,
){
    var expanded by rememberSaveable { mutableStateOf(false) }
    val vibrantColors = FloatingToolbarDefaults.vibrantFloatingToolbarColors()
    HorizontalFloatingToolbar(
        expanded = expanded,
        floatingActionButton = {
            TooltipBox(
                positionProvider =
                    TooltipDefaults.rememberTooltipPositionProvider(
                        TooltipAnchorPosition.Above
                    ),
                tooltip = {
                    PlainTooltip(
                        modifier = Modifier,
                        caretShape = TooltipDefaults.caretShape(),
                    ) {
                        Text("Localized description")
                    }
                },
                state = rememberTooltipState(),
                modifier = Modifier,
            ) {
                FloatingToolbarDefaults.VibrantFloatingActionButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(MaterialIcons.Filled.Add, contentDescription = "Localized description")
                }
            }
        },
        colors = vibrantColors,
        content = {
            TooltipBox(
                positionProvider =
                    TooltipDefaults.rememberTooltipPositionProvider(
                        TooltipAnchorPosition.Above
                    ),
                tooltip = {
                    PlainTooltip(
                        modifier =
                            Modifier
                    ) {
                        Text("Localized description")
                    }
                },
                state = rememberTooltipState(),
            ) {
                IconButton(
                    onClick = { onDeleteClick() },
                    Modifier.focusProperties { canFocus = expanded },
                ) {
                    Icon(MaterialIcons.Filled.Delete, contentDescription = "Localized description")
                }
            }
            TooltipBox(
                positionProvider =
                    TooltipDefaults.rememberTooltipPositionProvider(
                        TooltipAnchorPosition.Above
                    ),
                tooltip = {
                    PlainTooltip(
                        modifier =
                            Modifier
                    ) {
                        Text("Localized description")
                    }
                },
                state = rememberTooltipState(),
            ) {
                IconButton(
                    onClick = { onScannerClick() },
                    Modifier.focusProperties { canFocus = expanded },
                ) {
                    Icon(
                        MaterialIcons.Filled.QrCodeScanner,
                        contentDescription = "Localized description",
                    )
                }
            }
        },
    )
}



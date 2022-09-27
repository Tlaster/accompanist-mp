package moe.tlaster.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    properties: DialogProperties = DialogProperties()
) {
    AlertDialogImpl(
        onDismissRequest,
        confirmButton,
        modifier,
        dismissButton,
        icon,
        title,
        text,
        properties
    )
}

@Composable
expect fun AlertDialogImpl(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier,
    dismissButton: @Composable (() -> Unit)?,
    icon: @Composable (() -> Unit)?,
    title: @Composable (() -> Unit)?,
    text: @Composable (() -> Unit)?,
    properties: DialogProperties,
)

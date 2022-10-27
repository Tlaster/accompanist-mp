package moe.tlaster.dialog

import androidx.compose.material.AlertDialogProvider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Material3PopupAlertDialogProvider
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterialApi::class)
@Composable
actual fun DialogImpl(
    onDismissRequest: () -> Unit,
    properties: DialogProperties,
    content: @Composable () -> Unit
) {
    DialogImpl(
        onDismissRequest = onDismissRequest,
        content = content,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DialogImpl(
    onDismissRequest: () -> Unit,
    dialogProvider: AlertDialogProvider = Material3PopupAlertDialogProvider,
    content: @Composable () -> Unit
) {
    with(dialogProvider) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            content = content,
        )
    }
}

actual typealias DialogProperties = Any

@Suppress("FunctionName")
actual fun DialogProperties(
    dismissOnBackPress: Boolean,
    dismissOnClickOutside: Boolean,
): Any = Unit

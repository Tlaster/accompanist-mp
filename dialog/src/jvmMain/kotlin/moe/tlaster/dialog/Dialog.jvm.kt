package moe.tlaster.dialog

import androidx.compose.runtime.Composable

@Composable
actual fun DialogImpl(
    onDismissRequest: () -> Unit,
    properties: DialogProperties,
    content: @Composable DialogWindowScope.() -> Unit
) {
    androidx.compose.ui.window.Dialog(
        onCloseRequest = onDismissRequest,
        content = content,
    )
}

actual typealias DialogProperties = Any

@Suppress("FunctionName")
actual fun DialogProperties(
    dismissOnBackPress: Boolean,
    dismissOnClickOutside: Boolean,
): Any = Unit

actual typealias DialogWindowScope = androidx.compose.ui.window.DialogWindowScope

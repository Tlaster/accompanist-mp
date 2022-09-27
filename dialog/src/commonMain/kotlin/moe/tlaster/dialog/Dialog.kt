package moe.tlaster.dialog

import androidx.compose.runtime.Composable

@Composable
fun Dialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable DialogWindowScope.() -> Unit
) {
    DialogImpl(
        onDismissRequest,
        properties,
        content
    )
}

@Composable
internal expect fun DialogImpl(
    onDismissRequest: () -> Unit,
    properties: DialogProperties,
    content: @Composable DialogWindowScope.() -> Unit
)

expect class DialogProperties

expect fun DialogProperties(
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true
): DialogProperties

expect interface DialogWindowScope

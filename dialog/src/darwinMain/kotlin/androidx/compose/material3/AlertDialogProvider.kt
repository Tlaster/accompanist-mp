package androidx.compose.material3

import androidx.compose.runtime.Composable

interface AlertDialogProvider {
    /**
     * Dialog which will be used to place AlertDialog's [content].
     *
     * @param onDismissRequest Callback that will be called when the user closes the dialog
     * @param content Content of the dialog
     */
    @Composable
    fun AlertDialog(
        onDismissRequest: () -> Unit,
        content: @Composable () -> Unit
    )
}

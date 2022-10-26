package moe.tlaster.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

private val LocalDialogHost = staticCompositionLocalOf<ContentHolder> { error("No DialogHost") }

private class ContentHolder {
    private val _content = mutableStateOf<@Composable (() -> Unit)?>(null)
    val content: @Composable (() -> Unit)? by _content
    fun setContent(content: @Composable (() -> Unit)?) {
        _content.value = content
    }
}

@Composable
fun ProvideDialogHost(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier) {
        ProvideDialogHostInner(content)
    }
}

@Composable
fun BoxScope.ProvideDialogHostInner(
    content: @Composable () -> Unit
) {
    val dialogHost = remember {
        ContentHolder()
    }
    CompositionLocalProvider(
        LocalDialogHost provides dialogHost,
        content = content,
    )
    dialogHost.content?.invoke()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun DialogImpl(
    onDismissRequest: () -> Unit,
    properties: DialogProperties,
    content: @Composable DialogWindowScope.() -> Unit
) {
    val contentHolder = LocalDialogHost.current
    DisposableEffect(contentHolder) {
        contentHolder.setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DrawerDefaults.scrimColor)
                    .clickable(
                        onClick = {
                            onDismissRequest()
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                content.invoke(DialogWindowScope)
            }
        }
        onDispose {
            contentHolder.setContent(null)
        }
    }
}

actual typealias DialogProperties = Any

@Suppress("FunctionName")
actual fun DialogProperties(
    dismissOnBackPress: Boolean,
    dismissOnClickOutside: Boolean,
): Any = Unit

actual interface DialogWindowScope {
    companion object : DialogWindowScope
}

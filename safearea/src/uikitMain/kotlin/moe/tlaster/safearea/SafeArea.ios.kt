package moe.tlaster.safearea

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import platform.Foundation.NSNotification
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSSelectorFromString
import platform.Foundation.NSValue
import platform.UIKit.CGRectValue
import platform.UIKit.UIKeyboardAnimationDurationUserInfoKey
import platform.UIKit.UIKeyboardFrameEndUserInfoKey
import platform.UIKit.UIKeyboardWillHideNotification
import platform.UIKit.UIKeyboardWillShowNotification
import platform.UIKit.UIWindow
import platform.UIKit.safeAreaInsets
import platform.darwin.NSObject

@Composable
fun ProvideSafeArea(
    window: UIWindow,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val holder = remember(scope) { KeyboardStateHolder(scope) }
    DisposableEffect(window, holder) {
        NSNotificationCenter.defaultCenter.addObserver(
            holder,
            NSSelectorFromString("keyboardWillShow:"),
            UIKeyboardWillShowNotification,
            null
        )
        NSNotificationCenter.defaultCenter.addObserver(
            holder,
            NSSelectorFromString("keyboardWillHide:"),
            UIKeyboardWillHideNotification,
            null
        )
        onDispose {
            NSNotificationCenter.defaultCenter.removeObserver(holder)
        }
    }
    val baseSafeArea = remember(window) {
        window.safeAreaInsets().useContents {
            SafeArea(
                top = top.toFloat(),
                bottom = bottom.toFloat(),
                left = left.toFloat(),
                right = right.toFloat()
            )
        }
    }
    val keyboardHeight by holder.keyboardHeight
    val actualSafeArea = remember(baseSafeArea, keyboardHeight) {
        baseSafeArea.copy(bottom = maxOf(baseSafeArea.bottom, keyboardHeight))
    }
    CompositionLocalProvider(
        LocalSafeAreaProvider provides actualSafeArea
    ) {
        content.invoke()
    }
}

val LocalSafeAreaProvider = staticCompositionLocalOf<SafeArea> { error("No SafeArea") }

data class SafeArea(val top: Float, val bottom: Float, val left: Float, val right: Float)
private class KeyboardStateHolder(
    private val scope: CoroutineScope,
) : NSObject() {
    private val _keyboardHeight = Animatable(0f)
    val keyboardHeight: State<Float>
        get() = _keyboardHeight.asState()

    @ObjCAction
    fun keyboardWillShow(notification: NSNotification) {
        val userInfo = notification.userInfo ?: return
        scope.launch {
            val keyboardFrame = userInfo[UIKeyboardFrameEndUserInfoKey] as NSValue
            val duration = userInfo[UIKeyboardAnimationDurationUserInfoKey] as Double
            keyboardFrame.CGRectValue().useContents {
                _keyboardHeight.animateTo(size.height.toFloat(), animationSpec = tween(durationMillis = (duration * 1000).toInt(), easing = LinearOutSlowInEasing))
            }
        }
    }

    @ObjCAction
    fun keyboardWillHide(notification: NSNotification) {
        val userInfo = notification.userInfo ?: return
        scope.launch {
            val duration = userInfo[UIKeyboardAnimationDurationUserInfoKey] as Double
            _keyboardHeight.animateTo(0f, animationSpec = tween(durationMillis = (duration * 1000).toInt(), easing = LinearOutSlowInEasing))
        }
    }
}

actual val WindowInsets.Companion.topBar: WindowInsets
    @Composable
    @NonRestartableComposable
    get() = WindowInsets(
        left = 0.dp,
        top = LocalSafeAreaProvider.current.top.dp,
        right = 0.dp,
        bottom = 0.dp
    )
actual val WindowInsets.Companion.startBar: WindowInsets
    @Composable
    @NonRestartableComposable
    get() = WindowInsets(
        left = LocalSafeAreaProvider.current.left.dp,
        top = 0.dp,
        right = 0.dp,
        bottom = 0.dp
    )
actual val WindowInsets.Companion.endBar: WindowInsets
    @Composable
    @NonRestartableComposable
    get() = WindowInsets(
        left = 0.dp,
        top = 0.dp,
        right = LocalSafeAreaProvider.current.right.dp,
        bottom = 0.dp
    )
actual val WindowInsets.Companion.bottomBar: WindowInsets
    @Composable
    @NonRestartableComposable
    get() = WindowInsets(
        left = 0.dp,
        top = 0.dp,
        right = 0.dp,
        bottom = LocalSafeAreaProvider.current.bottom.dp
    )

package moe.tlaster.accompanist.sample

import androidx.compose.ui.window.Window
import platform.AppKit.NSApp

fun main() {
    Window(
        "Accompanist Sample",
    ) {
        App()
    }
    NSApp?.run()
}

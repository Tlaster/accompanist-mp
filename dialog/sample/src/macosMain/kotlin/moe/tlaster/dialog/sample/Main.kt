package moe.tlaster.dialog.sample

import androidx.compose.ui.window.Window
import platform.AppKit.NSApp
import platform.AppKit.NSApplication

fun main() {
    NSApplication.sharedApplication()
    Window("Sample") {
        App()
    }
    NSApp?.run()
}
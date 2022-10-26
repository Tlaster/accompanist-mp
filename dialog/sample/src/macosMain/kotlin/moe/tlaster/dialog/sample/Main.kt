package moe.tlaster.dialog.sample

import androidx.compose.ui.window.Window
import moe.tlaster.dialog.ProvideDialogHost
import platform.AppKit.NSApp
import platform.AppKit.NSApplication

fun main() {
    NSApplication.sharedApplication()
    Window("Sample") {
        ProvideDialogHost {
            App()
        }
    }
    NSApp?.run()
}
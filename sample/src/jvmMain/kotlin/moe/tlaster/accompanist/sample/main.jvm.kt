package moe.tlaster.accompanist.sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() {
    application {
        Window(
            title = "Accompanist Sample",
            onCloseRequest = ::exitApplication
        ) {
            App()
        }
    }
}

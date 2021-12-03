package de.jossiwolf.desktop

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.jossiwolf.common.YouTubeStatsApp

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        DesktopMaterialTheme {
            YouTubeStatsApp()
        }
    }
}

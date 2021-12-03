package de.jossiwolf.common

import androidx.compose.runtime.Composable
import de.jossiwolf.common.navigation.*
import de.jossiwolf.common.ui.stats.StatsScreen
import de.jossiwolf.common.util.navigation.NavHost

@Composable
fun YouTubeStatsApp() {
    NavHost<Screen>(initial = Screen.Stats) { navigator, screen ->
        when (screen) {
            is Screen.Intro -> IntroScreen(
                showStats = {
                    navigator.pop()
                    navigator.push(Screen.Stats)
                }
            )
            is Screen.Stats -> StatsScreen()
        }
    }
}

@Composable
fun IntroScreen(
    showStats: () -> Unit
) {

}

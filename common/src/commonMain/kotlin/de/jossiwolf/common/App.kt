package de.jossiwolf.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.jossiwolf.common.navigation.Screen
import de.jossiwolf.common.ui.intro.IntroScreen
import de.jossiwolf.common.ui.stats.StatsScreen
import de.jossiwolf.common.util.navigation.NavHost

@Composable
fun YouTubeStatsApp() {
    NavHost<Screen>(initial = Screen.Intro, Modifier.fillMaxSize()) { navigator, screen ->
        when (screen) {
            is Screen.Intro -> IntroScreen(
                showStats = { historyFilePath ->
                    navigator.pop()
                    navigator.push(Screen.Stats(historyFilePath))
                }
            )
            is Screen.Stats -> StatsScreen(screen.historyFilePath)
        }
    }
}

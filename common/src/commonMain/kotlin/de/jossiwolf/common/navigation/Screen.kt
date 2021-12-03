package de.jossiwolf.common.navigation

/**
 * The screens of the YT stats app
 */
sealed interface Screen {
    object Intro : Screen

    data class Stats(val historyFilePath: String): Screen
}

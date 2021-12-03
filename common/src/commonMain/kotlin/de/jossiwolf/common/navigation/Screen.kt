package de.jossiwolf.common.navigation

/**
 * The screens of the YT stats app
 */
sealed interface Screen {
    sealed interface Intro : Screen

    class Stats : Screen
}

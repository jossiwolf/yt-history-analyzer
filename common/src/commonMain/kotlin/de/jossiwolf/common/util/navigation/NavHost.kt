package de.jossiwolf.common.util.navigation

import androidx.compose.runtime.Composable

/**
 * Entry point to a composable navigation hierarchy.
 *
 * @param S The type of screen
 * @param backStack The [BackStack] maintained by the [navigator]
 * @param navigator The [Navigator] that maintains the [backStack]
 * @param backStackHost Slot for the [Composable] that will host the [content]. This can be used to
 * customize i.e. transitions between destinations.
 * @param content Slot for the content, with the associated [Navigator] and [S] screen as parameters
 */
@Composable
fun <S> NavHost(
    backStack: BackStack<S> = rememberBackStack(),
    navigator: Navigator<S> = rememberNavigator(backStack),
    backStackHost: BackStackHost<S> = DefaultBackStackHost(),
    content: @Composable (navigator: Navigator<S>, screen: S) -> Unit
) {
    backStackHost(backStack) { screen -> content(navigator, screen) }
}

typealias BackStackHost<S> = @Composable (
    backStack: BackStack<S>,
    content: @Composable (screen: S) -> Unit
) -> Unit

private fun <S> DefaultBackStackHost() =
    @Composable { backStack: BackStack<S>, content: @Composable (screen: S) -> Unit ->
        BackStack(backStack, content)
    }

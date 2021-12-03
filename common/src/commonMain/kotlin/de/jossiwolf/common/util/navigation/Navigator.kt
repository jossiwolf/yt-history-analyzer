package de.jossiwolf.common.util.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * A simple navigator interface for simple back stack handling
 *
 * @param push Executed when a push is requested, with the [S] instance that is requested to be oushed
 * @param pop The pop operation
 */
data class Navigator<S>(
    val push: (screen: S) -> Unit,
    val pop: () -> Unit
)

/**
 * Create and remember a [Navigator].
 *
 * @see Navigator
 */
@Composable
fun <S> rememberNavigator(
    push: (screen: S) -> Unit,
    pop: () -> Unit
) = remember { Navigator(push, pop) }

/**
 * Create and remember a [Navigator] from a [BackStack]. This produces a default navigator implementation
 * with the following back stack handling:
 * <b>Push</b>
 * When a push is requested, the entry is added as the topmost item of the [backStack].
 * Duplicate entries are allowed.
 * <b>Pop</b>
 * Calling [Navigator.pop] results in the topmost [backStack] entry being popped off.
 */
@Composable
fun <S> rememberNavigator(backStack: BackStack<S>) = rememberNavigator<S>(
    push = { screen -> backStack += screen },
    pop = { backStack.dropLast(1) }
)

/**
 * Pop up to the [upTo] entry in the [backStack].
 *
 * @param backStack The [BackStack] to pop
 * @param upTo The entry to pop up to, by default the topmost entry
 * @param inclusive Whether the [upTo] requested entry should be popped as well, or only all entries
 * up to it
 */
fun <S> Navigator<S>.pop(
    backStack: BackStack<S>,
    upTo: S = backStack.last(),
    inclusive: Boolean = true
) {
    while (backStack.last() != upTo) { pop() }
    if (inclusive) { pop() }
}

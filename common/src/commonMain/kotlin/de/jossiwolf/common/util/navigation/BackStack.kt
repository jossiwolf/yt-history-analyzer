package de.jossiwolf.common.util.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * A list-backed [BackStack] for a stack of type [S].
 */
class BackStack<S>(val backStackList: SnapshotStateList<S>) : MutableList<S> by backStackList {
    companion object {
        fun <S> Saver() = Saver<BackStack<S>, SnapshotStateList<S>>(
            save = { backStack -> backStack.backStackList },
            restore = { backStackList -> BackStack(backStackList) }
        )
    }
}

/**
 * Create and remember a [BackStack] and save and restore it across process recreation.
 *
 * @param inputs Inputs on whose changes to recreate the [BackStack] (and thus reset it)
 */
@Composable
fun <S> rememberBackStack(vararg inputs: Any?) = rememberSaveable(
    inputs,
    saver = BackStack.Saver()
) { BackStack<S>(backStackList = mutableStateListOf()) }

/**
 * Composable that hosts a [backStack] by invoking the [content] with the topmost item of the
 * [backStack].
 *
 * @param backStack The back stack
 * @param content Slot for the content, invoked with the topmost back stack entry
 */
@Composable
fun <S> BackStack(backStack: BackStack<S>, content: @Composable (screen: S) -> Unit) {
    content(backStack.last())
}

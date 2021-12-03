package de.jossiwolf.common.util.lce

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text

/**
 * A container to easily display different states represented by a [Lce]. Animates between states
 * using a crossfade animation. [empty], [error] and [loading] all have default implementations and
 * don't need to be supplied, but can be overridden.
 *
 * @param state The current value
 * @param modifier Optional [Modifier] for the entire component
 * @param empty [Composable] slot for the empty message to be displayed when [state] is [Lce.Empty]
 * @param error [Composable] slot for the error message to be displayed when [state] is [Lce.Error].
 * Invoked with the [Lce.Error] if any.
 * @param loading [Composable] slot for the loading indicator to be displayed when [state] is
 * [Lce.Loading]
 * @param animationSpec [FiniteAnimationSpec] to be used for animations between states
 * @param content The content to be displayed when [state] is [Lce.Content]. Invoked with the
 * [Lce.Content]'s [Lce.Content.data].
 *
 * @sample com.gamesworkshop.aos.components.samples.LceDataViewSample
 */
@Composable
fun <T> LceDataView(
    state: Lce<T>,
    modifier: Modifier = Modifier,
    empty: @Composable () -> Unit = { DefaultEmptyState() },
    error: @Composable (error: Lce.Error) -> Unit = { DefaultErrorState() },
    loading: @Composable () -> Unit = { DefaultLoadingState() },
    animationSpec: FiniteAnimationSpec<Float> = tween(),
    content: @Composable (data: T) -> Unit,
) {
    Crossfade(targetState = state, modifier, animationSpec) { state ->
        when (state) {
            is Lce.Content -> content(state.data)
            is Lce.Empty -> empty()
            is Lce.Error -> error(state)
            is Lce.Loading -> loading()
        }
    }
}

@Composable
private fun DefaultEmptyState() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("We don't have anything here yet... stay tuned!", textAlign = TextAlign.Center)
    }
}

@Composable
private fun DefaultErrorState() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Oh no, something went wrong :( Wanna try again?", textAlign = TextAlign.Center)
    }
}

@Composable
private fun DefaultLoadingState() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

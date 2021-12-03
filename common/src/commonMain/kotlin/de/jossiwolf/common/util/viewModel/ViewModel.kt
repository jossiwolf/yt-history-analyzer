@file:OptIn(ExperimentalTime::class)

package de.jossiwolf.common.util.viewModel

import de.jossiwolf.common.util.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlin.time.ExperimentalTime

abstract class ViewModel(dispatchers: Dispatchers = Dispatchers) {
    val viewModelScope = CoroutineScope(dispatchers.io())

    fun onClear() {
        viewModelScope.cancel("The ViewModel has been cleared.")
    }
}

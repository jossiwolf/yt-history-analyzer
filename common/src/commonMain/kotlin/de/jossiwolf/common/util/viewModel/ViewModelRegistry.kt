@file:OptIn(ExperimentalTime::class)
@file:Suppress("UNCHECKED_CAST")

package de.jossiwolf.common.util.viewModel

import de.jossiwolf.common.util.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * The [ViewModelRegistry] is a single place to keep track of [ViewModel] instances, allowing for
 * caching of instances and scheduled removal (removals with grace periods for destruction).
 */
interface ViewModelRegistry {
    fun add(viewModel: ViewModel, key: Any = viewModel.hashCode())

    /**
     * Get the [ViewModel] with the given [key].
     *
     * @throws [IllegalStateException] if no instance associated with the given [key] is present
     */
    @Throws(IllegalStateException::class)
    fun <VM : ViewModel> get(key: Any): VM

    fun <VM : ViewModel> getOrCreate(key: Any, factory: () -> VM): VM

    /**
     * Schedule the removal of the [viewModel] and destroy and remove it after the [gracePeriod]
     */
    @OptIn(ExperimentalTime::class)
    fun remove(viewModel: ViewModel, key: Any = viewModel.hashCode(), gracePeriod: Duration = Duration.seconds(5))
}

object DefaultViewModelRegistry : ViewModelRegistry {
    private val scope = CoroutineScope(Dispatchers.io())
    private val viewModels = mutableMapOf<Any, ViewModel>()
    private val scheduledForRemoval = mutableSetOf<ViewModel>()

    override fun add(viewModel: ViewModel, key: Any) {
        viewModels[key] = viewModel
    }

    override fun <VM : ViewModel> get(key: Any): VM = viewModels.getOrElse(key) {
        throw IllegalStateException("No ViewModel for the given $key was found")
    } as VM

    override fun <VM : ViewModel> getOrCreate(key: Any, factory: () -> VM) = viewModels.getOrPut(key) {
        factory()
    } as VM

    override fun remove(viewModel: ViewModel, key: Any, gracePeriod: Duration) {
        scope.launch {
            val alreadyScheduledForRemoval = scheduledForRemoval.add(viewModel)
            if (!alreadyScheduledForRemoval) {
                viewModels.remove(viewModel)
                delay(gracePeriod)
                try {
                    viewModel.onClear()
                } finally {
                    scheduledForRemoval.remove(viewModel)
                }
            }
        }
    }
}

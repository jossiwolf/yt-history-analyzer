package de.jossiwolf.common.util.lce

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

private typealias LceFlowErrorHandler<T> = suspend FlowCollector<Lce<T>>.(throwable: Throwable) -> Unit

/**
 * Error handling strategies for a [StateFlow] of [Lce]s produced using [asLceState]
 */
object LceFlowErrorHandlingStrategy {
    /**
     * Re-throw the caught [Throwable]
     */
    fun <T> reThrowCaughtError(): LceFlowErrorHandler<T> = { throwable -> throw throwable }

    /**
     * Log the stacktrace
     */
    fun <T> logError(): LceFlowErrorHandler<T> = { throwable -> throwable.printStackTrace() }

    /**
     * Log the stacktrace and map the [Throwable] into an [Lce.Error]
     */
    fun <T> logAndMapToLce(): LceFlowErrorHandler<T> = { throwable ->
        throwable.printStackTrace()
        emit(Lce.Error(throwable))
    }
}

/**
 * Converts a [Flow] of [T] to a [StateFlow] of [Lce]s of [T]
 *
 * Every element emitted from the original [Flow] is mapped into an [Lce.Content] or [Lce.Empty].
 * When the original [Flow] throws, the exception is caught, wrapped in an [Lce.Error] and then
 * emitted downstream.
 *
 * @param scope The [CoroutineScope] the [StateFlow]'s sharing will be started in
 * @param started The [SharingStarted] strategy
 * @param initialValue The resulting [StateFlow]'s initial value. [Lce.Loading] by default.
 * @param errorHandler The [LceFlowErrorHandler] used to [Flow.catch] [Throwable]s. Re-throws the
 * caught error by default. See [LceFlowErrorHandlingStrategy] for more available strategies.
 * @param contentOrEmpty The producer for the [Lce] instance based on the data
 */
private fun <T> Flow<T?>.asLceState(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(),
    initialValue: Lce<T> = Lce.Loading,
    errorHandler: LceFlowErrorHandler<T> = LceFlowErrorHandlingStrategy.reThrowCaughtError(),
    contentOrEmpty: (data: T?) -> Lce<T>,
): StateFlow<Lce<T>> = this
    .map { data -> contentOrEmpty(data) } // map our data to the Lce
    .onStart { emit(initialValue) }
    .catch(errorHandler) // emit Lce.Error when we catch something
    .onEmpty { emit(Lce.Empty) } // When the Flow is empty, we'll want to indicate that
    .stateIn(scope, started, initialValue) // Finally, let's convert to a StateFlow!

/**
 * Converts a [Flow] of [T] to a [StateFlow] of [Lce]s of [T]
 *
 * Every element emitted from the original [Flow] is mapped into an [Lce.Content] or [Lce.Empty].
 * When the original [Flow] throws, the exception is caught, wrapped in an [Lce.Error] and then
 * emitted downstream.
 *
 * @see asLceState
 */
@JvmName("flow_asLceState")
fun <T> Flow<T?>.asLceState(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(),
    errorHandler: LceFlowErrorHandler<T> = LceFlowErrorHandlingStrategy.reThrowCaughtError(),
    initialValue: Lce<T> = Lce.Loading
): StateFlow<Lce<T>> = this.asLceState(
    scope = scope,
    started = started,
    initialValue = initialValue,
    errorHandler = errorHandler,
    contentOrEmpty = Lce.Companion::contentOrEmpty
)

/**
 * Converts a [Flow] to a [StateFlow] of [Lce]s. Compared to our other [Flow.asLceState] helper,
 * this one specifically calls [Lce.contentOfListOrEmpty] instead of [Lce.contentOrEmpty].
 *
 * Every element emitted from the original [Flow] is mapped into an [Lce.Content] or [Lce.Empty].
 * When the original [Flow] throws, the exception is caught, wrapped in an [Lce.Error] and then
 * emitted downstream.
 *
 * @see asLceState
 */
@JvmName("flow_asLceListState")
fun <E, T : Collection<E>> Flow<T>.asLceState(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(),
    errorHandler: LceFlowErrorHandler<T> = LceFlowErrorHandlingStrategy.reThrowCaughtError(),
    initialValue: Lce<T> = Lce.Loading
): StateFlow<Lce<T>> = this.asLceState(
    scope = scope,
    started = started,
    initialValue = initialValue,
    errorHandler = errorHandler,
    contentOrEmpty = Lce.Companion::contentOfListOrEmpty
)

package de.jossiwolf.common.util

import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Batch the [operation], given an input [data], in batch sizes of [batchSize].
 *
 * @param data The input to be batched
 * @param batchSize The size of batches, by default the size of [data] divided by four
 * @param timeout The [Duration] to wait for after processing each batch
 * @param operation The operation to run with the given item of type [T]
 */
@OptIn(ExperimentalTime::class)
suspend inline fun <T, R> batch(
    data: Collection<T>,
    batchSize: Int = data.size / 4,
    timeout: Duration = Duration.milliseconds(1000),
    operation: (item: T) -> R
): List<R> = data
    .windowed(size = batchSize, step = batchSize, partialWindows = true)
    .flatMap { partialDataCollection ->
        val results = partialDataCollection.map { item -> operation(item) }
        delay(timeout)
        return@flatMap results
    }

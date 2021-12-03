@file:OptIn(ExperimentalTime::class)

package de.jossiwolf.common

import de.jossiwolf.common.util.batch
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class BatchTest {

    @Test
    fun testBatchTimeoutRespected() = runBlocking {
        val items = listOf(1, 2, 3, 4, 5, 6, 7, 8)
        val resultingItems = testRateLimit(limit = 9, timeFrame = Duration.milliseconds(1000)) {
            batch(items, timeout = Duration.seconds(1.5)) { x ->
                rateLimited { x * x }
            }
        }
        val expectedItems = items.map { x -> x * x }
        assert(resultingItems == expectedItems) {
            """The list items were expected to be equal.
                | Expected: $expectedItems
                | Actual: $resultingItems
            """.trimMargin()
        }
    }



}

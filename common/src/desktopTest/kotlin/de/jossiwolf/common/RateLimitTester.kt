package de.jossiwolf.common

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Helper class for testing a rate-limited operation.
 * @see [testRateLimit] as entry point
 *
 * @param limit The limit of invocations in a given [timeFrame], starting at the first
 * invocation of the [rateLimited] function.
 * @param timeFrame The time frame during which the rate limit applies and after which
 * the [invocations] count is reset.
 * @param configuration Additional specific configuration for the tester
 */
@OptIn(ExperimentalTime::class)
class RateLimitTester(
    val limit: Int,
    val timeFrame: Duration,
    val configuration: Configuration = Configuration.Default()
) {

    var rateLimitCallStartTime: Instant? = null
    var invocations = 0

    /**
     * Test a rate limited [operation]. Checks if the [limit] is hit and throws an
     * [IllegalStateException] if it is hit.
     */
    inline fun <R> rateLimited(operation: () -> R): R {
        if (rateLimitCallStartTime == null) {
            rateLimitCallStartTime = Clock.System.now()
        }
        val currentTime = Clock.System.now()
        val rateLimitResetTime = rateLimitCallStartTime!! + timeFrame
        if (currentTime > rateLimitResetTime) {
            resetRateLimitCallStartTime()
        }
        when (configuration.failSilently) {
            true -> println("E: The rate limit was hit")
            false -> check(invocations < limit) { "The rate limit was hit" }
        }
        invocations++
        return operation()
    }

    fun resetRateLimitCallStartTime() {
        rateLimitCallStartTime = null
        invocations = 0
    }

    /**
     * The configuration for the tester.
     *
     * @param failSilently Whether an exception should be thrown when the rate limit is hit
     */
    data class Configuration(
        val failSilently: Boolean = false
    ) {
        companion object {
            fun Default() = Configuration(failSilently = false)
        }
    }
}

@OptIn(ExperimentalTime::class)
suspend inline fun <R> testRateLimit(
    limit: Int,
    timeFrame: Duration,
    operation: RateLimitTester.() -> R
): R = RateLimitTester(limit, timeFrame).operation()

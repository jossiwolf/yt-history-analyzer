package de.jossiwolf.common.util

/**
 * Abstraction over the [kotlinx.coroutines.Dispatchers] object to be able to override/inject
 * dispatchers for testing
 */
interface Dispatchers {
    fun io() = kotlinx.coroutines.Dispatchers.IO
    fun unconfined() = kotlinx.coroutines.Dispatchers.Unconfined
    fun main() = kotlinx.coroutines.Dispatchers.Main
    fun default() = kotlinx.coroutines.Dispatchers.Default

    /**
     * Default implementation of the Dispatchers interface, delegating to the default Coroutines Dispatchers
     */
    companion object : Dispatchers
}

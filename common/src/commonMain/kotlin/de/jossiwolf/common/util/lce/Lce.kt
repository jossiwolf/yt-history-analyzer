package de.jossiwolf.common.util.lce

/**
 * Represents a resources' state
 * This is a simple model that should be used between ViewModel and UI Layer
 */
sealed class Lce<out T> {
    object Loading: Lce<Nothing>()
    data class Content<T>(val data: T) : Lce<T>()
    object Empty : Lce<Nothing>()
    data class Error(val throwable: Throwable, val message: String? = null) : Lce<Nothing>()

    companion object {

        /**
         * Produce a [Lce.Content] instance with the given [content] or [Lce.Empty] if the
         * [content] is null
         */
        fun <T> contentOrEmpty(content: T?): Lce<T> = when (content) {
            null -> Empty
            else -> Content(content)
        }

        /**
         * Produce a [Lce.Content] instance with the given [content] or [Lce.Empty] if the [content]
         * list is empty or null
         */
        fun <E, T : Collection<E>> contentOfListOrEmpty(content: T?): Lce<T> =
            if (content.isNullOrEmpty()) Empty else Content(content)
    }
}

fun<T> Lce<T>.getOrDefault(default: () -> T) = when (this) {
    is Lce.Content<T> -> this.data
    else -> default()
}

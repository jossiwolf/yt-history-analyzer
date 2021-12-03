package de.jossiwolf.common.util

/**
 * Simple helper for FS IO operations across multiple platforms
 */
expect class FileSystemHelper() {
    /**
     * Read the contents of a file
     */
    fun readContents(absolutePath: String): String
}

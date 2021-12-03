package de.jossiwolf.common.util.fs

/**
 * Simple helper for FS IO operations across multiple platforms
 */
expect class FileSystemHelper() {
    /**
     * Read the contents of a file
     */
    fun readContents(absolutePath: String): String
}

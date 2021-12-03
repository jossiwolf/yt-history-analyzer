package de.jossiwolf.common.util

import java.io.File

actual class FileSystemHelper {
    actual fun readContents(absolutePath: String): String {
        return File(absolutePath).readText()
    }
}

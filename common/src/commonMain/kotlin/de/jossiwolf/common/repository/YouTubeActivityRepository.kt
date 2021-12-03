package de.jossiwolf.common.repository

import de.jossiwolf.common.model.data.youtube.activity.YouTubeActivity
import de.jossiwolf.common.util.FileSystemHelper
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

interface YouTubeActivityRepository {
    suspend fun getActivity(): List<YouTubeActivity>
}

class FileBasedYouTubeActivityRepository(
    private val fileSystemAccessor: FileSystemHelper
) : YouTubeActivityRepository {
    override suspend fun getActivity(): List<YouTubeActivity> {
        val rawActivity =
            fileSystemAccessor.readContents("/Users/jossiwolf/Downloads/ytmusic_wrapped-python3/watch-history.json")
        return Json.decodeFromString(rawActivity)
    }
}

fun YouTubeActivityRepository() = FileBasedYouTubeActivityRepository(
    fileSystemAccessor = FileSystemHelper()
)

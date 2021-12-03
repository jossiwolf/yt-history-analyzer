package de.jossiwolf.common.repository

import de.jossiwolf.common.model.data.youtube.activity.YouTubeActivity
import de.jossiwolf.common.util.fs.FileSystemHelper
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

interface YouTubeActivityRepository {
    suspend fun getActivity(): List<YouTubeActivity>
}

class FileBasedYouTubeActivityRepository(
    private val watchHistoryFilePath: String = "/Users/jossiwolf/Downloads/ytmusic_wrapped-python3/watch-history.json",
    private val fileSystemAccessor: FileSystemHelper = FileSystemHelper()
) : YouTubeActivityRepository {
    override suspend fun getActivity(): List<YouTubeActivity> {
        val rawActivity =
            fileSystemAccessor.readContents(watchHistoryFilePath)
        return Json.decodeFromString(rawActivity)
    }
}

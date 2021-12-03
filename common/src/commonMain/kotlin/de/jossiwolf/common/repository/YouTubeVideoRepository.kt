package de.jossiwolf.common.repository

import de.jossiwolf.common.data.createDatabase
import de.jossiwolf.common.model.data.youtube.YouTubeResourceId
import de.jossiwolf.common.model.data.youtube.video.YouTubeVideoResource
import de.jossiwolf.common.youtube.YouTubeApi
import de.jossiwolf.common.youtube.YouTubeService
import de.jossiwolf.ytanalyzer.VideoDatabase
import de.jossiwolf.ytanalyzer.YoutubeVideo
import de.jossiwolf.common.data.DriverFactory

interface YouTubeVideoRepository {
    suspend fun getVideoInformation(resourceId: YouTubeResourceId): YouTubeVideoResource?
    suspend fun saveVideoInformation(videoResource: YouTubeVideoResource)
}

class DefaultYouTubeVideoRepository(
    private val api: YouTubeApi,
    private val database: VideoDatabase
) : YouTubeVideoRepository {
    override suspend fun getVideoInformation(resourceId: YouTubeResourceId): YouTubeVideoResource? {
        val videoResponse = api.getVideoInformation(resourceId)
        if (videoResponse.items.size > 1) {
            println("W: NetworkYouTubeVideoRepository received ${videoResponse.items.size} instead; picking the first item instead.")
        }
        //require(videoResponse.items.isNotEmpty()) { "The list of videos was empty" }
        return videoResponse.items.firstOrNull()
    }

    override suspend fun saveVideoInformation(videoResource: YouTubeVideoResource) {
        val video = YoutubeVideo(
            id = videoResource.id.rawId,
            duration = videoResource.contentDetails.duration,
            dimension = videoResource.contentDetails.dimension,
            definition = videoResource.contentDetails.definition,
            licensedContent = videoResource.contentDetails.licensedContent,
            regionRestriction = videoResource.contentDetails.regionRestriction?.allowed.orEmpty(),
            projection = videoResource.contentDetails.projection
        )
        database.youTubeVideoInformationQueries.insert(video)
    }
}

fun YouTubeVideoRepository(
    driverFactory: DriverFactory,
    apiEndpoint: String = "https://www.googleapis.com/youtube/v3",
    api: YouTubeApi = YouTubeService(apiEndpoint),
    database: VideoDatabase = createDatabase(driverFactory)
) = DefaultYouTubeVideoRepository(api, database)

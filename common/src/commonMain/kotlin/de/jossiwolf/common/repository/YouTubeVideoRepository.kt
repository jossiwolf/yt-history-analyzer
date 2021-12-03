package de.jossiwolf.common.repository

import de.jossiwolf.common.model.data.youtube.YouTubeResourceId
import de.jossiwolf.common.model.data.youtube.video.YouTubeVideoResource
import de.jossiwolf.common.youtube.YouTubeApi
import de.jossiwolf.common.youtube.YouTubeService

interface YouTubeVideoRepository {
    suspend fun getVideoInformation(resourceId: YouTubeResourceId): YouTubeVideoResource?
}

class NetworkYouTubeVideoRepository(
    private val api: YouTubeApi
) : YouTubeVideoRepository {
    override suspend fun getVideoInformation(resourceId: YouTubeResourceId): YouTubeVideoResource? {
        val videoResponse = api.getVideoInformation(resourceId)
        if (videoResponse.items.size > 1) {
            println("W: NetworkYouTubeVideoRepository received ${videoResponse.items.size} instead; picking the first item instead.")
        }
        //require(videoResponse.items.isNotEmpty()) { "The list of videos was empty" }
        return videoResponse.items.firstOrNull()
    }
}

fun YouTubeVideoRepository(
    apiEndpoint: String = "https://www.googleapis.com/youtube/v3",
    api: YouTubeApi = YouTubeService(apiEndpoint)
) = NetworkYouTubeVideoRepository(api)

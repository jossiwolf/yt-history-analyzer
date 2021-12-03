package de.jossiwolf.common.youtube

import de.jossiwolf.common.model.data.youtube.YouTubeApiResponse
import de.jossiwolf.common.model.data.youtube.YouTubeResourceId
import de.jossiwolf.common.model.data.youtube.video.YouTubeVideoInformationRequestType
import de.jossiwolf.common.model.data.youtube.video.YouTubeVideoResource
import de.jossiwolf.common.util.KtorService
import io.ktor.client.request.*

class YouTubeService(
    override val baseUrl: String = "https://www.googleapis.com/youtube/v3"
) : KtorService(), YouTubeApi {
    override suspend fun getVideoInformation(id: YouTubeResourceId): YouTubeApiResponse<YouTubeVideoResource> {
        val response = get<YouTubeApiResponse<YouTubeVideoResource>>("videos") {
            parameter("part", YouTubeVideoInformationRequestType.contentDetails)
            parameter("id", id.rawId)
            //parameter("key", apiKey)
        }
        return response
    }

    private val apiKey: String = TODO()
}

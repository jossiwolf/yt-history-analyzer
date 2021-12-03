package de.jossiwolf.common.model.data.youtube.video

import de.jossiwolf.common.model.data.youtube.YouTubeResourceId
import kotlinx.serialization.Serializable

@Serializable
data class YouTubeVideoResource(
    val kind: String,
    val etag: String,
    val id: YouTubeResourceId,
    val contentDetails: YouTubeVideoContentDetails
)


package de.jossiwolf.common.model.data.youtube.video

import kotlinx.serialization.Serializable

@Serializable
data class YouTubeVideoContentDetails(
    val duration: String,
    val dimension: YouTubeVideoDimension,
    val definition: YouTubeVideoDefinition,
    //val caption: Boolean,
    val licensedContent: Boolean,
    val regionRestriction: YouTubeVideoRegionRestriction? = null,
    val projection: YouTubeVideoProjection
)


package de.jossiwolf.common.model.data.youtube.video

import kotlinx.serialization.Serializable

@Serializable
data class YouTubeVideoRegionRestriction(
    val allowed: List<String> = emptyList()
)


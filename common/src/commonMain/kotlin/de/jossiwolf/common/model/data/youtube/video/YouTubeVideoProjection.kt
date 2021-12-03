package de.jossiwolf.common.model.data.youtube.video

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class YouTubeVideoProjection {
    @SerialName("rectangular")
    RECTANGULAR,

    @SerialName("square")
    SQUARE
}


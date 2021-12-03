package de.jossiwolf.common.model.data.youtube.video

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class YouTubeVideoDimension {
    @SerialName("2d")
    TWO_DIMENSIONAL,

    @SerialName("3d")
    THREE_DIMENSIONAL
}


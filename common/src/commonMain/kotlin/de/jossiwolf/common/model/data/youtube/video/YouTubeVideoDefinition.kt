package de.jossiwolf.common.model.data.youtube.video

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class YouTubeVideoDefinition {
    @SerialName("sd")
    SD,
    @SerialName("hd")
    HD
}


package de.jossiwolf.common.model.data.youtube

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class YouTubeResourceId(val rawId: String)

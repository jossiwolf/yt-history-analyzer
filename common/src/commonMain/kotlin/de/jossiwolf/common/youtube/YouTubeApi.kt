package de.jossiwolf.common.youtube

import de.jossiwolf.common.model.data.youtube.YouTubeApiResponse
import de.jossiwolf.common.model.data.youtube.YouTubeResourceId
import de.jossiwolf.common.model.data.youtube.video.YouTubeVideoResource
import de.jossiwolf.common.util.KtorService
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

interface YouTubeApi {
    suspend fun getVideoInformation(id: YouTubeResourceId): YouTubeApiResponse<YouTubeVideoResource>
}



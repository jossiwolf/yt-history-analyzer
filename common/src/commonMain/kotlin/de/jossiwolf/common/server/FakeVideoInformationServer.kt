package de.jossiwolf.common.server

import de.jossiwolf.common.model.data.youtube.YouTubeApiResponse
import de.jossiwolf.common.model.data.youtube.YouTubeResourceId
import de.jossiwolf.common.model.data.youtube.video.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlin.random.Random

val FAKE_SERVER_PORT = Random.nextInt(until = 9999)

val fakeYouTubeServer = embeddedServer(Netty, port = FAKE_SERVER_PORT) {
    install(ContentNegotiation) {
        json()
    }
    routing {
        get("/videos") {
            val requestedInformation = call.parameters["part"]
            val requestedId = call.parameters["id"]
            val apiKey = call.parameters["key"]
            val videoResource = YouTubeVideoResource(
                kind = "fake-kind",
                etag = "fake-etag-who-cares",
                id = YouTubeResourceId(requestedId!!),
                contentDetails = YouTubeVideoContentDetails(
                    duration = "PT3M22s",
                    dimension = YouTubeVideoDimension.TWO_DIMENSIONAL,
                    definition = YouTubeVideoDefinition.HD,
                    licensedContent = true,
                    regionRestriction = null,
                    projection = YouTubeVideoProjection.RECTANGULAR
                )
            )
            val response = YouTubeApiResponse(
                kind = "fake-kind",
                etag = "fake-etag",
                pageInfo = YouTubeApiResponse.PageInfo(totalResults = 1, resultsPerPage = 50),
                items = listOf(videoResource)
            )
            call.respond(response)
        }
    }
}

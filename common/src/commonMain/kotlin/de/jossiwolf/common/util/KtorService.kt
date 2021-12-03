package de.jossiwolf.common.util

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * A base implementation for network clients using Ktor - most importantly, attaching the [baseUrl] to requests
 * made using the request methods offered by [KtorService].
 *
 * @param httpClient The Ktor [HttpClient] to be used, by default a client with a [CIO] (coroutine-based IO) engine
 * @param json The [Json] configuration to be used for deserialization of http response bodies
 */
abstract class KtorService(
    val httpClient: HttpClient = HttpClient(CIO),
    val json: Json = Json { ignoreUnknownKeys = true }
) {
    /**
     * The service's base URL as a string.
     * Must not end with a slash (/).
     */
    abstract val baseUrl: String

    /**
     * Http.GET a resource at the given [baseUrl] + [path] and decode the JSON response body
     *
     * @param path The path of the resource
     * @param block Additional request configuration such as adding parameters
     * @see HttpClient.get
     */
    suspend inline fun <reified T> get(path: String, block: HttpRequestBuilder.() -> Unit = {}): T {
        val requestPath = path.removePrefix("/")
        val response = httpClient.get<HttpResponse>("$baseUrl/$requestPath", block)
        val responseText = response.readText()
        return json.decodeFromString(responseText)
    }
}

package de.jossiwolf.common.model.data.youtube

import kotlinx.serialization.Serializable

@Serializable
data class YouTubeApiResponse<I>(
    val kind: String,
    val etag: String,
    val nextPageToken: String? = null,
    val prevPageToken: String? = null,
    val pageInfo: PageInfo,
    val items: List<I>
) {
    @Serializable
    data class PageInfo(
        val totalResults: Int,
        val resultsPerPage: Int
    )
}

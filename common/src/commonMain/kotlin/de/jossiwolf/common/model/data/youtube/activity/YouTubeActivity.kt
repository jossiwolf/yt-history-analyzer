package de.jossiwolf.common.model.data.youtube.activity

import de.jossiwolf.common.model.data.youtube.YouTubeResourceId
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class YouTubeActivity(
    val header: String,
    val title: String,
    val titleUrl: String? = null,
    val subtitles: List<AssociatedTopic> = emptyList(),
    val time: Instant,
    val products: List<GoogleProduct>,
    val activityControls: List<String>
) {
    @Serializable
    data class AssociatedTopic(
        val name: String,
        val url: String? = null
    )
}



val YouTubeActivity.id: YouTubeResourceId
    get() {
        requireNotNull(titleUrl)
        val id = titleUrl.substringAfter(UNICODE_QUESTION_MARK)
        return YouTubeResourceId(id)
    }

private const val UNICODE_QUESTION_MARK = "watch?v="

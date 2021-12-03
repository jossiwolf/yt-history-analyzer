import de.jossiwolf.common.repository.YouTubeVideoRepository
import de.jossiwolf.common.youtube.YouTubeResourceId
import de.jossiwolf.common.youtube.YouTubeVideoResource

fun FakeVideoRepository(
    knownVideos: Map<YouTubeResourceId, YouTubeVideoResource>
) = object : YouTubeVideoRepository {
    override suspend fun getVideoInformation(resourceId: YouTubeResourceId) = knownVideos[resourceId]
}

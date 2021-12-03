import de.jossiwolf.common.youtube.YouTubeResourceId
import de.jossiwolf.common.youtube.YouTubeVideoContentDetails
import de.jossiwolf.common.fake.Fake
import kotlin.random.Random
import de.jossiwolf.common.youtube.YouTubeVideoResource as YouTubeVideoResourceModel

fun Fake.YouTubeVideoResource(
    kind: String = "fake-resource-kind",
    etag: String = "fake-etag",
    id: YouTubeResourceId = YouTubeResourceId(Random.nextLong().toString()),
    contentDetails: YouTubeVideoContentDetails = Fake.YouTubeVideoContentDetails()
) = YouTubeVideoResourceModel(kind = kind, etag = etag, id = id, contentDetails = contentDetails)

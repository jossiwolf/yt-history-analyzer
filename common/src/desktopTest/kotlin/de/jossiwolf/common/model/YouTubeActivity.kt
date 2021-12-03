import de.jossiwolf.common.model.GoogleProduct
import de.jossiwolf.common.model.YouTubeActivity as YouTubeActivityModel
import de.jossiwolf.common.youtube.YouTubeResourceId
import de.jossiwolf.common.fake.Fake
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

fun Fake.YouTubeActivity(
    header: String = "YouTube Music",
    title: String = "Watched Lianne La Havas - Bittersweet",
    titleUrl: String = "https://www.youtube.com/watch?v\\003dF7wIRxQEetc",
    subtitles: List<YouTubeActivityModel.AssociatedTopic> = listOf(Fake.AssociatedTopic("Lianne La Havas - Topic")),
    time: Instant = Clock.System.now(),
    products: List<GoogleProduct> = listOf(GoogleProduct.YOUTUBE),
    activityControls: List<String> = emptyList()
) = YouTubeActivityModel(
    header = header,
    title = title,
    titleUrl = titleUrl,
    subtitles = subtitles,
    time = time,
    products = products,
    activityControls = activityControls
)

fun Fake.YouTubeActivity(
    header: String = "YouTube Music",
    title: String = "Watched Lianne La Havas - Bittersweet",
    id: YouTubeResourceId,
    subtitles: List<YouTubeActivityModel.AssociatedTopic> = listOf(Fake.AssociatedTopic("Lianne La Havas - Topic")),
    time: Instant = Clock.System.now(),
    products: List<GoogleProduct> = listOf(GoogleProduct.YOUTUBE),
    activityControls: List<String> = emptyList()
) = YouTubeActivityModel(
    header = header,
    title = title,
    titleUrl = "https://www.youtube.com/watch?v\\003d${id.rawId}",
    subtitles = subtitles,
    time = time,
    products = products,
    activityControls = activityControls
)

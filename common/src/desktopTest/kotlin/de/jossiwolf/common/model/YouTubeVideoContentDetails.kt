import de.jossiwolf.common.youtube.YouTubeVideoDefinition
import de.jossiwolf.common.youtube.YouTubeVideoDimension
import de.jossiwolf.common.youtube.YouTubeVideoProjection
import de.jossiwolf.common.youtube.YouTubeVideoRegionRestriction
import de.jossiwolf.common.fake.Fake
import de.jossiwolf.common.youtube.YouTubeVideoContentDetails as YouTubeVideoContentDetailsModel

fun Fake.YouTubeVideoContentDetails(
    duration: String = "PT2M22S",
    dimension: YouTubeVideoDimension = YouTubeVideoDimension.TWO_DIMENSIONAL,
    definition: YouTubeVideoDefinition = YouTubeVideoDefinition.HD,
    caption: Boolean = false,
    licensedContent: Boolean = false,
    regionRestriction: YouTubeVideoRegionRestriction = Fake.YouTubeVideoRegionRestriction(),
    projection: YouTubeVideoProjection = YouTubeVideoProjection.RECTANGULAR
) = YouTubeVideoContentDetailsModel(
    duration = duration,
    dimension = dimension,
    definition = definition,
    caption = caption,
    licensedContent = licensedContent,
    regionRestriction = regionRestriction,
    projection = projection
)

import de.jossiwolf.common.fake.Fake
import de.jossiwolf.common.youtube.YouTubeVideoRegionRestriction as YouTubeVideoRegionRestrictionModel

fun Fake.YouTubeVideoRegionRestriction(
    allowed: List<String> = listOf("DE", "UK", "US", "ES")
) = YouTubeVideoRegionRestrictionModel(allowed)

import de.jossiwolf.common.model.YouTubeActivity
import de.jossiwolf.common.fake.Fake

fun Fake.AssociatedTopic(
    name: String = "This is a fake topic",
    url: String? = null
) = YouTubeActivity.AssociatedTopic(name = name, url = url)

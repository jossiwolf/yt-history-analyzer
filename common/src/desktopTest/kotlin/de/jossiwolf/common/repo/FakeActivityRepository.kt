import de.jossiwolf.common.model.YouTubeActivity
import de.jossiwolf.common.repository.YouTubeActivityRepository
import de.jossiwolf.common.fake.Fake


fun FakeActivityRepository(
    activities: List<YouTubeActivity> = listOf(Fake.YouTubeActivity(), Fake.YouTubeActivity())
) = object : YouTubeActivityRepository {
    override suspend fun getActivity() = activities
}

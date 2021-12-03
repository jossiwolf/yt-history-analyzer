package de.jossiwolf.common.stats

import FakeActivityRepository
import FakeVideoRepository
import YouTubeActivity
import YouTubeVideoResource
import de.jossiwolf.common.fake.Fake
import de.jossiwolf.common.model.data.youtube.YouTubeResourceId
import de.jossiwolf.common.ui.stats.StatsViewModel
import de.jossiwolf.common.ui.stats.WatchStatisticsScreenState
import de.jossiwolf.common.util.lce.Lce
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class StatsViewModelTest {

    @Test
    fun testSomething(): Unit = runBlocking {
        val given = Given(videoIds = listOf(YouTubeResourceId("F7wIRxQEetc"), YouTubeResourceId("F7wIRxQEetc")))
        val viewModel = StatsViewModel(from = given)
        val states = viewModel.state
            .take(2)
            .toList()

        assert(states[0] is Lce.Loading)
        val secondState = states[1] as? Lce.Content<WatchStatisticsScreenState>
        assert(secondState is Lce.Content)
        secondState!!.data
    }

    class Given(
        val videoIds: List<YouTubeResourceId>
    ) {
        val activities = videoIds.map { videoId -> Fake.YouTubeActivity(id = videoId) }
        val videos = videoIds.map { videoId -> Fake.YouTubeVideoResource(id = videoId) }
        val activityRepository = FakeActivityRepository(activities)
        val videoRepository = FakeVideoRepository(videos.associateBy { video -> video.id })
    }

    fun StatsViewModel(from: Given) =
        StatsViewModel(activityRepository = from.activityRepository, videoRepository = from.videoRepository)
}

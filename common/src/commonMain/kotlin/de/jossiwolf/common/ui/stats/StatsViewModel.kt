@file:OptIn(ExperimentalTime::class, ExperimentalTime::class)

package de.jossiwolf.common.ui.stats

import de.jossiwolf.common.model.data.youtube.YouTubeResourceId
import de.jossiwolf.common.model.data.youtube.activity.YouTubeActivity
import de.jossiwolf.common.model.data.youtube.activity.id
import de.jossiwolf.common.model.data.youtube.video.YouTubeVideoResource
import de.jossiwolf.common.repository.YouTubeActivityRepository
import de.jossiwolf.common.repository.YouTubeVideoRepository
import de.jossiwolf.common.util.batch
import de.jossiwolf.common.util.lce.asLceState
import de.jossiwolf.common.util.viewModel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class StatsViewModel(
    activityRepository: YouTubeActivityRepository,
    youTubeApiEndpoint: String = "http://localhost:80",
    private val videoRepository: YouTubeVideoRepository = YouTubeVideoRepository(youTubeApiEndpoint),
    private val rateLimitConfiguration: RateLimitConfiguration = RateLimitConfiguration.NoLimit()
) : ViewModel() {

    private val cachedVideoInformation = mutableMapOf<YouTubeResourceId, YouTubeVideoResource>()

    private suspend fun FlowCollector<WatchStatisticsScreenState>.analyzeWatchStatistics(
        watchActivities: List<YouTubeActivity>
    ) {
        val watchedVideoStatistics = watchActivities
            .groupBy { activity -> activity.title }
            .mapKeys { (videoTitle, _) -> videoTitle.removePrefix("Watched ") }
            .mapValues { (_, videoActivities) -> videoActivities.size }
            .map { (videoTitle, timesWatched) -> WatchActivity(videoTitle, timesWatched) }
            .sortedByDescending { activity -> activity.timesWatched }
        val mostWatchedVideo = watchedVideoStatistics.first()
        val medianWatchCount = watchedVideoStatistics.size / mostWatchedVideo.timesWatched
        val mostWatchedVideos = watchedVideoStatistics.takeWhile { activity ->
            activity.timesWatched > medianWatchCount
        }
        val maxMessageDisplayTime = 1000
        mostWatchedVideos
            .shuffled()
            .forEach { video ->
                val messageDisplayTime =
                    maxMessageDisplayTime * 1L - (video.timesWatched - mostWatchedVideo.timesWatched)
                emit(
                    WatchStatisticsScreenState(
                        groups = emptyList(),
                        loadingMessage = "Oh wow! You have been listening to ${video.title} a lot. Yikes. ${video.timesWatched} times??"
                    )
                )
                delay(messageDisplayTime)
            }
    }

    val state = flow {
        val activity = activityRepository.getActivity()
        val groups = activity
            .groupBy { it.header }
            .map { (name, watchActivities) ->
                val uniqueVideos = watchActivities
                    .distinctBy { it.title }
                    .filter { it.titleUrl != null }
                val uniqueVideoIds = uniqueVideos.associateBy { it.id }
                val cachedVideoIds = cachedVideoInformation.keys
                val nonCachedVideoIds = uniqueVideoIds.keys subtract cachedVideoIds
                emit(
                    WatchStatisticsScreenState(
                        groups = emptyList(),
                        loadingMessage = "Fetching video information for ${nonCachedVideoIds.size} videos :)"
                    )
                )
                analyzeWatchStatistics(watchActivities)


                var fetchedVideos = 0
                val freshVideoInformation = getFreshVideoInformation(
                    forVideoIds = nonCachedVideoIds,
                    rateLimitConfiguration = rateLimitConfiguration,
                    onEach = {
                        val loadingMessage =
                            "Fetching video information for ${nonCachedVideoIds.size} videos. \n $fetchedVideos already done :)"
                        val progress = fetchedVideos / nonCachedVideoIds.size
                        //emit(WatchStatisticsScreenState(groups = emptyList(), loadingMessage = loadingMessage))
                        fetchedVideos++
                    }
                )

                val requestedVideoInformationFromCache = cachedVideoInformation
                    .filterKeys { id -> id !in cachedVideoIds }
                    .values
                val allVideoInformation = freshVideoInformation + requestedVideoInformationFromCache
                val videoStatistics = allVideoInformation.map { video ->
                    val title = uniqueVideoIds[video.id]!!.title.removePrefix("Watched ")
                    val timesWatched = watchActivities
                        .filter { it.titleUrl != null }
                        .filter { watchedVideo -> watchedVideo.id == video.id }
                        .size
                    WatchActivity(title = title, timesWatched = timesWatched)
                }.sortedByDescending { activity -> activity.timesWatched }
                StatisticGroup(name, videoStatistics)
            }
        val state = WatchStatisticsScreenState(groups, loadingMessage = null)
        emit(state)
    }.asLceState(viewModelScope, SharingStarted.Lazily)

    private suspend fun getFreshVideoInformation(
        forVideoIds: Collection<YouTubeResourceId>,
        rateLimitConfiguration: RateLimitConfiguration = RateLimitConfiguration.Default(),
        onEach: suspend () -> Unit
    ) = batch(
        forVideoIds,
        batchSize = rateLimitConfiguration.batchSize,
        timeout = rateLimitConfiguration.timeout
    ) { videoId ->
        onEach()
        val video = videoRepository.getVideoInformation(videoId)
        if (video != null) {
            cachedVideoInformation[video.id] = video
        }
        return@batch video
    }.filterNotNull()

    data class RateLimitConfiguration(val batchSize: Int = 500, val timeout: Duration) {
        companion object {
            fun Gentle() = RateLimitConfiguration(batchSize = 300, timeout = Duration.milliseconds(1500))
            fun Default() = RateLimitConfiguration(batchSize = 500, timeout = Duration.milliseconds(900))
            fun Fast() = RateLimitConfiguration(batchSize = 1500, timeout = Duration.milliseconds(0))
            fun NoLimit(batchSize: Int = 5000) =
                RateLimitConfiguration(batchSize = batchSize, timeout = Duration.milliseconds(0))
        }
    }
}



package de.jossiwolf.common.ui.stats

data class WatchStatisticsScreenState(
    val groups: List<StatisticGroup>,
    val loadingMessage: String?,
)

data class StatisticGroup(
    val name: String,
    val entries: List<WatchActivity>
)

data class WatchActivity(
    val title: String,
    val timesWatched: Int
)

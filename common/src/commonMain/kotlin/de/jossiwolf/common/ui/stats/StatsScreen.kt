package de.jossiwolf.common.ui.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.jossiwolf.common.repository.YouTubeActivityRepository
import de.jossiwolf.common.server.fakeYouTubeServer
import de.jossiwolf.common.util.lce.Lce
import de.jossiwolf.common.util.lce.LceDataView
import de.jossiwolf.common.util.lce.getOrDefault
import de.jossiwolf.common.util.viewModel.viewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StatsScreen() {
    DisposableEffect(Unit) {
        fakeYouTubeServer.start()
        onDispose { fakeYouTubeServer.stop(0, 1000) }
    }
    val viewModel = viewModel<StatsViewModel> { StatsViewModel(YouTubeActivityRepository()) }
    val statsState by viewModel.goodState.collectAsState()

    var selectedTab by remember(statsState) {
        val state = statsState.getOrDefault { null }
        val firstHeader = state?.groups?.firstOrNull()?.name
        mutableStateOf(firstHeader ?: "")
    }

    val topBar: @Composable () -> Unit = when (val stats = statsState) {
        is Lce.Content -> when (stats.data.loadingMessage) {
            null -> (@Composable {
                val selectedTabIndex by remember { derivedStateOf { stats.data.groups.indexOfFirst { it.name == selectedTab }} }
                TabRow(selectedTabIndex = selectedTabIndex) {
                    stats.data.groups.forEach { group ->
                        Tab(
                            selected = selectedTab == group.name,
                            onClick = { selectedTab = group.name },
                            modifier = Modifier.padding(vertical = 16.dp)
                        ) { Text(group.name) }
                    }
                }
            })
            else -> (@Composable { })
        }
        else -> (@Composable {})
    }

    Scaffold(
        topBar = topBar
    ) { innerPadding ->
        LceDataView(statsState) { state ->
            if (state.groups.any { it.name == selectedTab }) {
                LazyColumn(Modifier.padding(innerPadding)) {
                    items(state.groups.find { it.name == selectedTab }!!.entries) { statEntry ->
                        ListItem(
                            text = { Text(statEntry.title) },
                            secondaryText = { Text("You watched this video ${statEntry.timesWatched} times") }
                        )
                    }
                }
            }
            if (state.loadingMessage != null) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(16.dp))
                    Text(state.loadingMessage, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

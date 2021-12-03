package de.jossiwolf.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import de.jossiwolf.common.ui.stats.StatsScreen

@Preview
@Composable
fun AppPreview() {
    StatsScreen("/Users/jossiwolf/Downloads/ytmusic_wrapped-python3/watch-history.json")
}

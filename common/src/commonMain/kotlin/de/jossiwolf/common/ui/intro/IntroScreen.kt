package de.jossiwolf.common.ui.intro

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.jossiwolf.common.util.fs.FilePicker

sealed interface IntroScreen {
    object Welcome : IntroScreen
    object PickHistoryFile : IntroScreen
}

@Composable
fun IntroScreen(
    showStats: (historyFilePath: String) -> Unit
) {
    var content: IntroScreen by remember { mutableStateOf(IntroScreen.Welcome) }
    when (content) {
        IntroScreen.PickHistoryFile -> PickHistoryFile(showStats)
        IntroScreen.Welcome -> Welcome(showNextPage = {
            content = IntroScreen.PickHistoryFile
        })
    }
}

@Composable
fun Welcome(showNextPage: () -> Unit) {
    Column {
        Text("Welcome! Let's analyze your YouTube Music history!")
        Spacer(Modifier.height(64.dp))
        FloatingActionButton(onClick = showNextPage) {
            Icon(Icons.Default.ArrowForward, "Show next page")
        }
    }
}

data class PickHistoryFileScreenState(
    val errorMessage: String?
)

@Composable
fun PickHistoryFile(onFilePicked: (historyFilePath: String) -> Unit) {
    var state by remember { mutableStateOf(PickHistoryFileScreenState(errorMessage = null)) }
    Column {
        Text("Where can we find your watch history?")
        if (state.errorMessage != null) {
            Text(state.errorMessage!!)
        }
        Spacer(Modifier.height(64.dp))
        FilePicker(onFilePicked = { pickedFileName ->
            when (pickedFileName) {
                null -> state = PickHistoryFileScreenState(errorMessage = "Please select a file :)")
                else -> onFilePicked(pickedFileName)
            }
        })
    }
}

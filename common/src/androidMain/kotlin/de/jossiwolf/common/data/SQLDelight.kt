package de.jossiwolf.common.data

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import de.jossiwolf.ytanalyzer.VideoDatabase

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(VideoDatabase.Schema, context, "videos.db")
    }
}

@Composable
actual fun rememberDriverFactory(): DriverFactory {
    val context = LocalContext.current
    return remember(context) { DriverFactory(context) }
}

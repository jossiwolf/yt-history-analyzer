package de.jossiwolf.common.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import de.jossiwolf.ytanalyzer.VideoDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        VideoDatabase.Schema.create(driver)
        return driver
    }
}

@Composable
actual fun rememberDriverFactory() = remember { DriverFactory() }

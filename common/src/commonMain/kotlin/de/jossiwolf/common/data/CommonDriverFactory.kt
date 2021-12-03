package de.jossiwolf.common.data

import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.EnumColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import de.jossiwolf.ytanalyzer.VideoDatabase
import de.jossiwolf.ytanalyzer.YoutubeVideo

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

val listOfStringsAdapter = object : ColumnAdapter<List<String>, String> {
    override fun decode(databaseValue: String) = if (databaseValue.isEmpty()) {
        listOf()
    } else {
        databaseValue.split(",")
    }

    override fun encode(value: List<String>) = value.joinToString(separator = ",")
}

fun createDatabase(driverFactory: DriverFactory): VideoDatabase {
    val driver = driverFactory.createDriver()
    return VideoDatabase(
        driver, YoutubeVideo.Adapter(
            dimensionAdapter = EnumColumnAdapter(),
            definitionAdapter = EnumColumnAdapter(),
            regionRestrictionAdapter = listOfStringsAdapter,
            projectionAdapter = EnumColumnAdapter()
        )
    )
}

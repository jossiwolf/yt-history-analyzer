package de.jossiwolf.common.model.data.youtube.activity

import kotlinx.serialization.Serializable

@Serializable
enum class GoogleActivityControl {;

    enum class YouTube {
        WATCH_HISTORY
    }
}

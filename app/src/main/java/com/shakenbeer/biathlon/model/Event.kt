package com.shakenbeer.biathlon.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Suppress("CanBeParameter")
@Entity(tableName = "event")
class Event(val seasonId: String,
            @PrimaryKey val eventId: String,
            val startDate: Date,
            val endDate: Date,
            val description: String,
            val shortDescription: String,
            val organizer: String,
            val nat: String) {

    @Ignore
    val dates = if (startDate != SIWI_NO_START_DATE && endDate != SIWI_NO_END_DATE) {
        startTimeFormatter.format(startDate) + " - " + endTimeFormatter.format(endDate)
    } else {
        "Dates undefined"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event

        if (eventId != other.eventId) return false

        return true
    }

    override fun hashCode(): Int {
        return eventId.hashCode()
    }

    companion object {
        private val startTimeFormatter = SimpleDateFormat("d MMM", Locale.US)
        private val endTimeFormatter = SimpleDateFormat("d MMM yyyy", Locale.US)

        const val SIWI_NO_SEASON_ID = "SIWI_NO_SEASON_ID"
        const val SIWI_NO_EVENT_ID = "SIWI_NO_EVENT_ID"
        val SIWI_NO_START_DATE = Date(0)
        val SIWI_NO_END_DATE = Date(0)
        const val SIWI_NO_EVENT_DESCRIPTION = "SIWI_NO_EVENT_DESCRIPTION"
        const val SIWI_NO_EVENT_SHORT_DESCRIPTION = "SIWI_NO_EVENT_SHORT_DESCRIPTION"
        const val SIWI_NO_EVENT_ORGANIZER = "SIWI_NO_EVENT_ORGANIZER"
        const val SIWI_NO_EVENT_NAT = "SIWI_NO_EVENT_NAT"
    }
}
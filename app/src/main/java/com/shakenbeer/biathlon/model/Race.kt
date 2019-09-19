package com.shakenbeer.biathlon.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "race")
class Race(val eventId: String,
           @PrimaryKey val raceId: String,
           val shortDescription: String,
           val startTime: Date,
           val km: String) {

    @Ignore
    val start: String = if (startTime != SIWI_NO_RACE_START_TIME) {
        startTimeFormatter.format(startTime)
    } else {
        "Start time unknown"
    }

    companion object {
        private val startTimeFormatter = SimpleDateFormat("d MMM yy, E, HH:mm", Locale.US)
        const val SIWI_NO_RACE_ID = "SIWI_NO_RACE_ID"
        const val SIWI_NO_RACE_SHORT_DESCRIPTION = "SIWI_NO_RACE_SHORT_DESCRIPTION"
        val SIWI_NO_RACE_START_TIME = Date(0)
        const val SIWI_NO_KM = "--"
    }
}
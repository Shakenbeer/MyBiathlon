package com.shakenbeer.biathlon.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "race")
class Race(val eventId: String,
           @PrimaryKey val raceId: String,
           val shortDesciption: String,
           val startTime: Date) {

    companion object {
        const val SIWI_NO_RACE_ID = "SIWI_NO_RACE_ID"
        const val SIWI_NO_RACE_SHORT_DESCRIPTION = "SIWI_NO_RACE_SHORT_DESCRIPTION"
        val SIWI_NO_RACE_START_TIME = Date(0)
    }
}
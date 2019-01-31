package com.shakenbeer.biathlon.season.domain

import com.shakenbeer.biathlon.model.Event
import com.shakenbeer.biathlon.model.Race

interface SeasonRepo {
    fun getEvents(seasonId: String, level: Int): List<Event>

    fun getRaces(eventId: String): List<Race>
}
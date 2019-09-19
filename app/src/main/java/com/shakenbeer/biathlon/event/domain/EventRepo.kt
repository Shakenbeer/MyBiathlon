package com.shakenbeer.biathlon.event.domain

import com.shakenbeer.biathlon.model.Event
import com.shakenbeer.biathlon.model.Race

interface EventRepo {
    fun getEventWithRaces(eventId: String): Pair<Event, List<Race>>
}
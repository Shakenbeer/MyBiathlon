package com.shakenbeer.biathlon.room

import androidx.annotation.WorkerThread
import com.shakenbeer.biathlon.model.Event
import com.shakenbeer.biathlon.model.Race
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@WorkerThread
class RoomFacade @Inject constructor(private val eventDao: EventDao,
                                     private val raceDao: RaceDao) {

    fun areSeasonEventsCached(seasonId: String) = eventDao.getEventsCount(seasonId) > 0

    fun saveSeasonEvents(events: List<Event>) = eventDao.save(events)

    fun getSeasonEvents(seasonId: String) = eventDao.loadSeasonEvents(seasonId)

    fun getEvent(eventId: String) = eventDao.getEvent(eventId)

    fun areEventRacesCached(eventId: String) = raceDao.getRacesCount(eventId) > 0

    fun saveEventRaces(races: List<Race>) = raceDao.save(races)

    fun getEventsRaces(eventId: String) = raceDao.loadEventRaces(eventId)
}
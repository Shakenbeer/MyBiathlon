package com.shakenbeer.biathlon.season.data

import com.shakenbeer.biathlon.model.Event
import com.shakenbeer.biathlon.model.Race
import com.shakenbeer.biathlon.rest.BiathlonResults
import com.shakenbeer.biathlon.rest.DatacenterException
import com.shakenbeer.biathlon.room.RoomFacade
import com.shakenbeer.biathlon.season.domain.SeasonRepo
import com.shakenbeer.biathlon.shared.siwiToEvent
import com.shakenbeer.biathlon.shared.siwiToRace

class CachingSeasonRepo(private val biathlonResults: BiathlonResults,
                        private val roomFacade: RoomFacade) : SeasonRepo {

    override fun getEvents(seasonId: String, level: Int): List<Event> {
        if (!roomFacade.areSeasonEventsCached(seasonId)) {
            val call = biathlonResults.events(seasonId, level)
            val response = call.execute()
            if (response.isSuccessful) {
                response.body()?.run {
                    roomFacade.saveSeasonEvents(map { siwiToEvent(it) })
                }
            } else {
                throw DatacenterException(if (response.errorBody() != null)
                    response.errorBody().toString()
                else "Unknown server exception")
            }
        }
        return roomFacade.getSeasonEvents(seasonId)
    }

    override fun getRaces(eventId: String): List<Race> {
       if (!roomFacade.areEventRacesCached(eventId)) {
           val call = biathlonResults.races(eventId)
           val response = call.execute()
           if (response.isSuccessful) {
               response.body()?.run {
                   roomFacade.saveEventRaces(map { siwiToRace(it, eventId) })
               }
           } else {
               throw DatacenterException(if (response.errorBody() != null)
                   response.errorBody().toString()
               else "Unknown server exception")
           }
       }
        return roomFacade.getEventsRaces(eventId)
    }
}


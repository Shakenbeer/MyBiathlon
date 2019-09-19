package com.shakenbeer.biathlon.event.data

import com.shakenbeer.biathlon.event.domain.EventRepo
import com.shakenbeer.biathlon.model.Event
import com.shakenbeer.biathlon.model.Race
import com.shakenbeer.biathlon.rest.BiathlonResults
import com.shakenbeer.biathlon.rest.DatacenterException
import com.shakenbeer.biathlon.room.RoomFacade
import com.shakenbeer.biathlon.shared.siwiToRace

class CachingEventRepo(private val biathlonResults: BiathlonResults,
                       private val roomFacade: RoomFacade) : EventRepo {
    override fun getEventWithRaces(eventId: String): Pair<Event, List<Race>> {
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
        return Pair(roomFacade.getEvent(eventId), roomFacade.getEventsRaces(eventId))
    }
}
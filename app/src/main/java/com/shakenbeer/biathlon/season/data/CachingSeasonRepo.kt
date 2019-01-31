package com.shakenbeer.biathlon.season.data

import com.shakenbeer.biathlon.model.Event
import com.shakenbeer.biathlon.model.Event.Companion.SIWI_NO_END_DATE
import com.shakenbeer.biathlon.model.Event.Companion.SIWI_NO_EVENT_DESCRIPTION
import com.shakenbeer.biathlon.model.Event.Companion.SIWI_NO_EVENT_ID
import com.shakenbeer.biathlon.model.Event.Companion.SIWI_NO_EVENT_NAT
import com.shakenbeer.biathlon.model.Event.Companion.SIWI_NO_EVENT_ORGANIZER
import com.shakenbeer.biathlon.model.Event.Companion.SIWI_NO_EVENT_SHORT_DESCRIPTION
import com.shakenbeer.biathlon.model.Event.Companion.SIWI_NO_SEASON_ID
import com.shakenbeer.biathlon.model.Event.Companion.SIWI_NO_START_DATE
import com.shakenbeer.biathlon.model.Race
import com.shakenbeer.biathlon.model.Race.Companion.SIWI_NO_RACE_ID
import com.shakenbeer.biathlon.model.Race.Companion.SIWI_NO_RACE_SHORT_DESCRIPTION
import com.shakenbeer.biathlon.model.Race.Companion.SIWI_NO_RACE_START_TIME
import com.shakenbeer.biathlon.rest.BiathlonResults
import com.shakenbeer.biathlon.rest.DatacenterException
import com.shakenbeer.biathlon.rest.model.SIWIEvent
import com.shakenbeer.biathlon.rest.model.SIWIRace
import com.shakenbeer.biathlon.room.RoomFacade
import com.shakenbeer.biathlon.season.domain.SeasonRepo

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
                   roomFacade.saveEventRaces(map { siwiToRace(it, eventId)})
               }
           } else {
               throw DatacenterException(if (response.errorBody() != null)
                   response.errorBody().toString()
               else "Unknown server exception")
           }
       }
        return roomFacade.getEventsRaces(eventId)
    }

    companion object {
        @JvmStatic
        fun siwiToEvent(siwiEvent: SIWIEvent) =
                Event(siwiEvent.seasonId ?: SIWI_NO_SEASON_ID,
                        siwiEvent.eventId ?: SIWI_NO_EVENT_ID,
                        siwiEvent.startDate ?: SIWI_NO_START_DATE,
                        siwiEvent.endDate ?: SIWI_NO_END_DATE,
                        siwiEvent.description ?: SIWI_NO_EVENT_DESCRIPTION,
                        siwiEvent.shortDescription ?: SIWI_NO_EVENT_SHORT_DESCRIPTION,
                        siwiEvent.organizer ?: SIWI_NO_EVENT_ORGANIZER,
                        siwiEvent.nat ?: SIWI_NO_EVENT_NAT)

        fun siwiToRace(siwiRace: SIWIRace, eventId: String) =
                Race(eventId, siwiRace.raceId ?: SIWI_NO_RACE_ID,
                        siwiRace.shortDescription ?: SIWI_NO_RACE_SHORT_DESCRIPTION,
                        siwiRace.startTime ?: SIWI_NO_RACE_START_TIME)
    }


}


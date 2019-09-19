@file:Suppress("RemoveRedundantBackticks")

package com.shakenbeer.biathlon.season.domain

import com.google.gson.reflect.TypeToken
import com.shakenbeer.biathlon.Utils.gson
import com.shakenbeer.biathlon.Utils.stringFromFile
import com.shakenbeer.biathlon.model.*
import com.shakenbeer.biathlon.rest.model.SIWIEvent
import com.shakenbeer.biathlon.rest.model.SIWIRace
import com.shakenbeer.biathlon.shared.addHours
import com.shakenbeer.biathlon.shared.siwiToEvent
import com.shakenbeer.biathlon.shared.siwiToRace
import org.apache.commons.lang3.time.DateUtils
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

class GetSeasonUseCaseTest {

    val events: List<Event>
    val races2: List<Race>
    val races9: List<Race>
    private val seasonRepo: SeasonRepo
    private val getSeasonUseCase: GetSeasonUseCase

    init {
        val json = stringFromFile("1718.json")
        val json2 = stringFromFile("1718_2.json")
        val json9 = stringFromFile("1718_9.json")
        val eventsType = object : TypeToken<List<SIWIEvent>>() {}.type
        val racesType = object : TypeToken<List<SIWIRace>>() {}.type
        events = gson.fromJson<List<SIWIEvent>>(json, eventsType).map { siwiToEvent(it) }
        races2 = gson.fromJson<List<SIWIRace>>(json2, racesType)
                .map { siwiToRace(it, "BT1718SWRLCP02") }
        races9 = gson.fromJson<List<SIWIRace>>(json9, racesType)
                .map { siwiToRace(it, "BT1718SWRLCP09") }
        seasonRepo = object : SeasonRepo {
            override fun getRaces(eventId: String) = if (eventId == "BT1718SWRLCP02") races2 else races9
            override fun getEvents(seasonId: String, level: Int) = events
        }
        getSeasonUseCase = GetSeasonUseCase(seasonRepo)
    }

    @Test
    fun `if after season end then return past season`() {
        val date = DateUtils.addDays(events[events.size - 1].endDate, 1)
        val whatsNext = getSeasonUseCase.whatsNext(date, events)

        assertTrue(whatsNext is PastSeason)
        assertTrue((whatsNext as PastSeason).startDate == events[0].startDate)
        assertTrue(whatsNext.endDate == events[events.size - 1].endDate)
    }

    @Test
    fun `if last date of season and after last race then return past season`() {
        val date = races9.last().startTime.addHours(1)
        val whatsNext = getSeasonUseCase.whatsNext(date, events)

        assertTrue(whatsNext is PastSeason)
        assertTrue((whatsNext as PastSeason).startDate == events.first().startDate)
        assertTrue(whatsNext.endDate == events.last().endDate)
    }

    @Test
    fun `if last date of season and before last race then return next race`() {
        val date = DateUtils.addHours(races9.last().startTime, -1)
        val whatsNext = getSeasonUseCase.whatsNext(date, events)

        assertTrue(whatsNext is NextRace)
        assertTrue((whatsNext as NextRace).startTime == races9[races9.size - 1].startTime)
    }

    @Test
    fun `if date is in event and after last race then return next event`() {
        val date = DateUtils.addHours(races2.last().startTime, 1)
        val whatsNext = getSeasonUseCase.whatsNext(date, events)

        assertTrue(whatsNext is NextEvent)
        assertTrue((whatsNext as NextEvent).startTime == DateUtils.truncate(events[2].startDate, Calendar.DATE))
    }

    @Test
    fun `if date is in event between races then return next race`() {
        val date = DateUtils.addHours(races2[1].startTime, 2)
        val whatsNext = getSeasonUseCase.whatsNext(date, events)

        assertTrue(whatsNext is NextRace)
        assertTrue((whatsNext as NextRace).startTime == races2[2].startTime)
    }

    @Test
    fun `if date is in event before first race then return next race`() {
        val date = DateUtils.addHours(races2[0].startTime, -1)
        val whatsNext = getSeasonUseCase.whatsNext(date, events)

        assertTrue(whatsNext is NextRace)
        assertTrue((whatsNext as NextRace).startTime == races2[0].startTime)
    }

    @Test
    fun `if date is between two events then return next event`() {
        val date = DateUtils.addDays(events[0].endDate, 1)
        val whatsNext = getSeasonUseCase.whatsNext(date, events)

        assertTrue(whatsNext is NextEvent)
        assertTrue((whatsNext as NextEvent).startTime == DateUtils.truncate(events[1].startDate, Calendar.DATE))
    }

    @Test
    fun `if date is before first event then return next season`() {
        val date = DateUtils.addDays(events[0].startDate, -123)
        val whatsNext = getSeasonUseCase.whatsNext(date, events)

        assertTrue(whatsNext is NextSeason)
        assertTrue((whatsNext as NextSeason).startTime == DateUtils.truncate(events[0].startDate, Calendar.DATE))
    }


}

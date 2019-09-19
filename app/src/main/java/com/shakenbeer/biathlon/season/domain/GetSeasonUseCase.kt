package com.shakenbeer.biathlon.season.domain

import com.shakenbeer.biathlon.model.*
import com.shakenbeer.biathlon.shared.addDays
import com.shakenbeer.biathlon.shared.trimTime
import java.util.*
import javax.inject.Inject

class GetSeasonUseCase
@Inject constructor (private val seasonRepo: SeasonRepo) {

    fun execute(seasonId: String, level: Int): Pair<WhatsNext, List<Event>> {
        val events = seasonRepo.getEvents(seasonId, level)
        val whatsNext = whatsNext(Date(), events)
        return Pair(whatsNext, events)
    }

    internal fun whatsNext(date: Date, events: List<Event>): WhatsNext {

        if (beforeFirstDayOfSeason(date, events)) {
            return NextSeason(events.first().startDate.trimTime())
        }

        val event = events.find { it.endDate.trimTime().addDays(1).after(date) }
        if (event != null) {
            return if (date.before(event.startDate.trimTime())) {
                NextEvent(event.startDate.trimTime())
            } else {
                val races = seasonRepo.getRaces(event.eventId)
                val race = races.find { it.startTime.after(date) }
                if (race != null) {
                    NextRace(race.startTime)
                } else {
                    if (event == events.last()) {
                        PastSeason(events.first().startDate, events.last().endDate)
                    } else {
                        NextEvent(events[events.indexOf(event) + 1].startDate.trimTime())
                    }
                }
            }
        } else {
            return PastSeason(events.first().startDate, events.last().endDate)
        }
    }

    private fun beforeFirstDayOfSeason(date: Date, events: List<Event>) =
            date.time < events.first().startDate.trimTime().time

}
package com.shakenbeer.biathlon.event.domain

import javax.inject.Inject

class GetEventUseCase @Inject constructor(private val eventRepo: EventRepo) {
    fun execute(eventId: String) = eventRepo.getEventWithRaces(eventId)
}
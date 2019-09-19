package com.shakenbeer.biathlon.event.presentation

import com.shakenbeer.biathlon.model.Event
import com.shakenbeer.biathlon.model.Race

sealed class EventViewState
object LoadingState: EventViewState()
class ErrorState(val throwable: Throwable): EventViewState()
class RacesState(val event: Event, val races: List<Race>): EventViewState()
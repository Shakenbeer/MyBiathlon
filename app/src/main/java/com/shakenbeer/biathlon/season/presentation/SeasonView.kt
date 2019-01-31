package com.shakenbeer.biathlon.season.presentation

import com.shakenbeer.biathlon.model.Event
import com.shakenbeer.biathlon.model.PastSeason
import com.shakenbeer.biathlon.ui.MvpView

interface SeasonView : MvpView {
    fun showEvents(events: List<Event>, anotherSeason: Boolean)
    fun showError(throwable: Throwable)
    fun showPastSeason(pastSeason: PastSeason)
    fun showTimer()
    fun tickTimer(days: Long, hours: Long, minutes: Long, seconds: Long, what: String)
}
package com.shakenbeer.biathlon.season.presentation

import com.shakenbeer.biathlon.FeatureScope
import com.shakenbeer.biathlon.model.*
import com.shakenbeer.biathlon.season.domain.GetSeasonUseCase
import com.shakenbeer.biathlon.ui.BasePendingPresenter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@FeatureScope
class SeasonPresenter
@Inject constructor(private val getSeasonUseCase: GetSeasonUseCase) : BasePendingPresenter<SeasonView>() {

    private var cache: Pair<WhatsNext, List<Event>>? = null
    private val disposables = CompositeDisposable()
    private var timer: Disposable? = null
    private var days = 0L
    private var hours = 0L
    private var minutes = 0L
    private var seconds = 0L
    private var name = ""

    fun obtainEvents(seasonId: String, level: Int) {
        disposables.add(
                Single.fromCallable { getSeasonUseCase.execute(seasonId, level) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ season ->
                            cache = season
                            updateView { it.showEvents(season.second, true) }
                            processWhatNext(season.first)
                        }, { throwable ->
                            updateView { view -> view.showError(throwable) }
                        })
        )
    }

    private fun processWhatNext(whatsNext: WhatsNext) {
        timer?.dispose()
        if (whatsNext is PastSeason) {
            updateView { it.showPastSeason(whatsNext) }
        } else {
            updateView { it.showTimer() }
            prepareTimer(whatsNext)
        }
    }

    private fun prepareTimer(whatsNext: WhatsNext) {
        val (startTime: Date, event: String) = when (whatsNext) {
            is NextSeason -> {
                Pair(whatsNext.startTime, "season")
            }
            is NextEvent -> {
                Pair(whatsNext.startTime, "event")
            }
            is NextRace -> {
                Pair(whatsNext.startTime, "race")
            }
            else -> {
                throw IllegalStateException("We're looking forward!")
            }
        }
        name = event
        timer = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val now = Date()
                    if (now.before(startTime)) {
                        val diff = startTime.time - now.time
                        days = diff / MILLIS_IN_DAY
                        hours = (diff % MILLIS_IN_DAY) / MILLIS_IN_HOUR
                        minutes = (diff % MILLIS_IN_HOUR) / MILLIS_IN_MINUTE
                        seconds = (diff % MILLIS_IN_MINUTE) / 1000
                        updateView { seasonView ->  seasonView.tickTimer(days, hours, minutes, seconds, name) }
                    }
                }
        timer?.let { disposables.add(it) }
    }

    override fun restore(view: SeasonView) {
        cache?.let {
            view.showEvents(it.second, false)
            view.tickTimer(days, hours, minutes, seconds, name)
            processWhatNext(it.first)
        }
    }

    override fun destroy() {
        disposables.clear()
    }

    companion object {
        private val MILLIS_IN_DAY = TimeUnit.DAYS.toMillis(1)
        private val MILLIS_IN_HOUR = TimeUnit.HOURS.toMillis(1)
        private val MILLIS_IN_MINUTE = TimeUnit.MINUTES.toMillis(1)
    }

}
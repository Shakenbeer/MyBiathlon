package com.shakenbeer.biathlon.event.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakenbeer.biathlon.event.domain.GetEventUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventViewModel
@Inject constructor(private val getEventUseCase: GetEventUseCase) : ViewModel() {

    //We need property for Dispatchers.IO to replace it in tests
    //until issue https://github.com/Kotlin/kotlinx.coroutines/issues/982 fixed
    @Suppress("MemberVisibilityCanBePrivate")
    var ioDispatcher = Dispatchers.IO

    private val racesLiveData = MutableLiveData<EventViewState>()

    val races: LiveData<EventViewState>
        get() = racesLiveData

    @Suppress("MemberVisibilityCanBePrivate")
    internal fun obtainRaces(eventId: String) {
        viewModelScope.launch {
            try {
                val (event, races) = withContext(ioDispatcher) {
                    getEventUseCase.execute(eventId)
                }
                racesLiveData.value = RacesState(event, races)
            } catch (t: Throwable) {
                racesLiveData.value = ErrorState(t)
            }
        }
    }
}
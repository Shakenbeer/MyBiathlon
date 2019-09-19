package com.shakenbeer.biathlon.event

import com.shakenbeer.biathlon.event.data.CachingEventRepo
import com.shakenbeer.biathlon.event.domain.EventRepo
import com.shakenbeer.biathlon.event.presentation.EventViewModel
import com.shakenbeer.biathlon.rest.BiathlonResults
import com.shakenbeer.biathlon.room.RoomFacade
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class EventModule {

    @Provides
    fun provideEventRepo(biathlonResults: BiathlonResults, roomFacade: RoomFacade): EventRepo =
            CachingEventRepo(biathlonResults, roomFacade)

    @Provides
    fun provideEventViewModelFactory(provider: Provider<EventViewModel>): EventViewModelFactory =
            EventViewModelFactory(provider)
}

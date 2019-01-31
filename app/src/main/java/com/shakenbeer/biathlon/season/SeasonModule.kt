package com.shakenbeer.biathlon.season

import com.shakenbeer.biathlon.FeatureScope
import com.shakenbeer.biathlon.rest.BiathlonResults
import com.shakenbeer.biathlon.room.RoomFacade
import com.shakenbeer.biathlon.season.data.CachingSeasonRepo
import com.shakenbeer.biathlon.season.domain.SeasonRepo
import dagger.Module
import dagger.Provides

@Module
class SeasonModule {

    @Provides
    @FeatureScope
    fun provideSeasonRepo(biathlonResults: BiathlonResults, roomFacade: RoomFacade): SeasonRepo =
            CachingSeasonRepo(biathlonResults, roomFacade)
}

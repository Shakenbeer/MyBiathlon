package com.shakenbeer.biathlon

import com.shakenbeer.biathlon.event.EventComponent
import com.shakenbeer.biathlon.event.EventModule
import com.shakenbeer.biathlon.season.SeasonComponent
import com.shakenbeer.biathlon.season.SeasonModule
import com.shakenbeer.biathlon.settings.UserSettingsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class), (UserSettingsModule::class)])
interface ApplicationComponent {

    operator fun plus(seasonModule: SeasonModule): SeasonComponent

    operator fun plus(eventModule: EventModule): EventComponent
}

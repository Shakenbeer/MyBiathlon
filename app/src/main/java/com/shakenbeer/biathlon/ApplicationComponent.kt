package com.shakenbeer.biathlon

import dagger.Component
import com.shakenbeer.biathlon.season.SeasonComponent
import com.shakenbeer.biathlon.season.SeasonModule
import com.shakenbeer.biathlon.settings.UserSettingsModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class), (UserSettingsModule::class)])
interface ApplicationComponent {

    operator fun plus(seasonModule: SeasonModule): SeasonComponent
}

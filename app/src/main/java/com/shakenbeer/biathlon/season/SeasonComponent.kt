package com.shakenbeer.biathlon.season

import dagger.Subcomponent
import com.shakenbeer.biathlon.FeatureScope
import com.shakenbeer.biathlon.season.presentation.SeasonActivity

@FeatureScope
@Subcomponent(modules = [(SeasonModule::class)])
interface SeasonComponent {
    fun inject(seasonActivity: SeasonActivity)
}

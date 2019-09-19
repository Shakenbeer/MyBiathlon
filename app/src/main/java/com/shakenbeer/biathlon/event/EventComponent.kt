package com.shakenbeer.biathlon.event

import com.shakenbeer.biathlon.event.presentation.EventActivity
import dagger.Subcomponent

@Subcomponent(modules = [(EventModule::class)])
interface EventComponent {
    fun inject(eventActivity: EventActivity)
}

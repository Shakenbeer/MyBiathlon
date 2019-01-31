package com.shakenbeer.biathlon

import android.app.Application

class BoilerplateApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}

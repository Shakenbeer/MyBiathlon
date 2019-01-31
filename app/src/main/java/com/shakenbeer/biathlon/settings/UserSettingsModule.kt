package com.shakenbeer.biathlon.settings

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserSettingsModule {

    @Provides
    @Singleton
    fun provideUserSettings(context: Context): UserSettings = LocalUserSettings(context)
}
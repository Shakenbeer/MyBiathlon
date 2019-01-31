package com.shakenbeer.biathlon.settings

import androidx.annotation.DrawableRes

interface UserSettings {
    @DrawableRes
    fun getFlagRes(): Int
    fun getTeam(): String
    fun setTeam(team: String)
}
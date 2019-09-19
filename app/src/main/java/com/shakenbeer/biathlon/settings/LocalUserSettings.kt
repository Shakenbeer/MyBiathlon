package com.shakenbeer.biathlon.settings

import android.content.Context
import android.content.SharedPreferences
import com.shakenbeer.biathlon.R
import com.shakenbeer.biathlon.shared.Const.AUSTRIA
import com.shakenbeer.biathlon.shared.Const.BELARUS
import com.shakenbeer.biathlon.shared.Const.CHINA
import com.shakenbeer.biathlon.shared.Const.CZECH_REPUBLIC
import com.shakenbeer.biathlon.shared.Const.FINLAND
import com.shakenbeer.biathlon.shared.Const.FRANCE
import com.shakenbeer.biathlon.shared.Const.GERMANY
import com.shakenbeer.biathlon.shared.Const.IBU
import com.shakenbeer.biathlon.shared.Const.ITALY
import com.shakenbeer.biathlon.shared.Const.NEUTRAL
import com.shakenbeer.biathlon.shared.Const.NORWAY
import com.shakenbeer.biathlon.shared.Const.POLAND
import com.shakenbeer.biathlon.shared.Const.SLOVENIA
import com.shakenbeer.biathlon.shared.Const.SWEDEN
import com.shakenbeer.biathlon.shared.Const.UKRAINE
import com.shakenbeer.biathlon.shared.Const.USA
import javax.inject.Inject

class LocalUserSettings @Inject constructor(val context: Context) : UserSettings {

    private val flags: Map<String, Int> = mapOf(
            NEUTRAL to 0,
            IBU to R.drawable.ibu_background,
            AUSTRIA to R.drawable.austria_background,
            BELARUS to R.drawable.belarus_background,
            CHINA to R.drawable.china_background,
            CZECH_REPUBLIC to R.drawable.czech_background,
            FRANCE to R.drawable.france_background,
            FINLAND to R.drawable.finland_background,
            GERMANY to R.drawable.germany_background,
            ITALY to R.drawable.italy_background,
            NORWAY to R.drawable.norway_background,
            POLAND to R.drawable.poland_background,
            SLOVENIA to R.drawable.slovenia_background,
            SWEDEN to R.drawable.sweden_background,
            UKRAINE to R.drawable.ukraine_background,
            USA to R.drawable.usa_background)

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, 0)

    override fun getFlagRes(): Int = flags[getTeam()] ?: 0

    override fun getTeam(): String = prefs.getString(KEY_FLAG_INDEX, NEUTRAL) ?: NEUTRAL

    override fun setTeam(team: String) = prefs.edit().putString(KEY_FLAG_INDEX, team).apply()

    companion object {
        const val PREFERENCES_NAME = "local_settings"
        const val KEY_FLAG_INDEX = "flag_index"
    }
}
package com.shakenbeer.biathlon.event.presentation

import com.shakenbeer.biathlon.R
import com.shakenbeer.biathlon.model.Race
import com.shakenbeer.biathlon.ui.BaseBindingAdapter
import javax.inject.Inject

class RaceAdapter @Inject constructor(): BaseBindingAdapter<Race>() {

    override fun getObjForPosition(position: Int) = items[position]

    override fun getLayoutIdForPosition(position: Int) = R.layout.item_race
}
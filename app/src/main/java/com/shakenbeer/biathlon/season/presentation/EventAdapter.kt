package com.shakenbeer.biathlon.season.presentation

import com.shakenbeer.biathlon.R
import com.shakenbeer.biathlon.model.Event
import com.shakenbeer.biathlon.ui.BaseBindingAdapter
import javax.inject.Inject

class EventAdapter @Inject constructor(): BaseBindingAdapter<Event>() {

    override fun getObjForPosition(position: Int) = items[position]

    override fun getLayoutIdForPosition(position: Int) = R.layout.item_event
}
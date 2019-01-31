package com.shakenbeer.biathlon.ui

import com.shakenbeer.biathlon.R
import javax.inject.Inject

class SingleLayoutAdapter @Inject constructor(): BaseBindingAdapter() {

    override fun getObjForPosition(position: Int) = items[position]

    override fun getLayoutIdForPosition(position: Int) = R.layout.item_event
}
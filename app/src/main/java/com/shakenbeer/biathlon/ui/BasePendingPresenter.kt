package com.shakenbeer.biathlon.ui

import androidx.annotation.UiThread
import java.util.*

abstract class BasePendingPresenter<T : MvpView> : BasePresenter<T>() {

    private val updates = LinkedList<(T) -> Unit>()

    override fun attachView(view: T) {
        super.attachView(view)
        dispatchViewUpdates()
    }

    @UiThread
    fun updateView(pendingUpdate: (T) -> Unit) {
        updates.add(pendingUpdate)
        dispatchViewUpdates()
    }

    private fun dispatchViewUpdates() {
        if (isViewAttached) {
            while (!updates.isEmpty()) {
                updates.poll().invoke(mvpView!!)
            }
        }
    }
}

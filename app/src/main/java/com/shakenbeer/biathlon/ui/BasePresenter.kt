package com.shakenbeer.biathlon.ui

abstract class BasePresenter<T : MvpView> : MvpPresenter<T> {

    var mvpView: T? = null
        private set

    val isViewAttached: Boolean
        get() = mvpView != null

    override fun attachView(view: T) {
        mvpView = view
    }

    override fun detachView() {
        mvpView = null
    }

    abstract fun restore(view: T)

    abstract fun destroy()
}

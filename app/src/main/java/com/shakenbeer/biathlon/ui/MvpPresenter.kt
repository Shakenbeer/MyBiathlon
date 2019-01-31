package com.shakenbeer.biathlon.ui

interface MvpPresenter<in T : MvpView> {

    fun attachView(view: T)

    fun detachView()
}

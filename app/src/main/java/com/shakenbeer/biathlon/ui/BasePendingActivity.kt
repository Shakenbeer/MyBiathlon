package com.shakenbeer.biathlon.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
abstract class BasePendingActivity<V : MvpView, out P : BasePendingPresenter<V>> : AppCompatActivity() {

    abstract fun pendingPresenter(): P

    override fun onStart() {
        super.onStart()
        @Suppress("UNCHECKED_CAST")
        pendingPresenter().attachView(this as V)
    }

    override fun onStop() {
        super.onStop()
        pendingPresenter().detachView()
    }
}

package com.shakenbeer.biathlon.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.shakenbeer.biathlon.event.presentation.EventViewModel

import javax.inject.Inject
import javax.inject.Provider

class EventViewModelFactory @Inject
constructor(private val eventViewModelProvider: Provider<EventViewModel>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel: ViewModel
        if (modelClass == EventViewModel::class.java) {
            viewModel = eventViewModelProvider.get()
        } else {
            throw RuntimeException("unsupported view model class: $modelClass")
        }

        @Suppress("UNCHECKED_CAST")
        return viewModel as T
    }
}

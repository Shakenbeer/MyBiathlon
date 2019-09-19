package com.shakenbeer.biathlon.event.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakenbeer.biathlon.BiathlonApplication
import com.shakenbeer.biathlon.R
import com.shakenbeer.biathlon.databinding.ItemEventBinding
import com.shakenbeer.biathlon.event.EventComponent
import com.shakenbeer.biathlon.event.EventModule
import com.shakenbeer.biathlon.event.EventViewModelFactory
import com.shakenbeer.biathlon.settings.UserSettings
import kotlinx.android.synthetic.main.activity_event.*
import javax.inject.Inject

class EventActivity : AppCompatActivity() {

    @Inject
    lateinit var eventViewModelFactory: EventViewModelFactory
    @Inject
    lateinit var adapter: RaceAdapter
    @Inject
    lateinit var userSettings: UserSettings

    private var eventBinding: ItemEventBinding? = null

    private val eventComponent: EventComponent by lazy {
        (application as BiathlonApplication)
                .component + EventModule()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        eventBinding = DataBindingUtil.bind<ItemEventBinding>(eventView)
        eventBinding?.itemDelimiter?.isVisible = false
        eventComponent.inject(this)

        rootLayout.setBackgroundResource(userSettings.getFlagRes())

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        eventRecyclerView.layoutManager = LinearLayoutManager(this)
        eventRecyclerView.adapter = adapter

        val eventViewModel: EventViewModel = ViewModelProvider(this, eventViewModelFactory)
                .get(EventViewModel::class.java)
        eventViewModel.races.observe(this, Observer { onViewState(it) })
        eventViewModel.obtainRaces(intent.getStringExtra(EXTRA_EVENT_ID) ?:
            throw IllegalStateException("Activity has been started without requiered extra param."))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun onViewState(eventViewState: EventViewState) {
        when (eventViewState) {
            is LoadingState -> {
            }
            is ErrorState -> Toast.makeText(this, eventViewState.throwable.localizedMessage,
                    LENGTH_LONG).show()
            is RacesState -> {
                eventBinding?.obj = eventViewState.event
                adapter.items = eventViewState.races.toMutableList()
            }
        }
    }

    companion object {
        private const val EXTRA_EVENT_ID = "com.shakenbeer.biathlon.event.presentation.eventIdExtra"

        fun start(context: Context, eventId: String) {
            context.startActivity(
                    Intent(context, EventActivity::class.java)
                            .putExtra(EXTRA_EVENT_ID, eventId))
        }
    }
}

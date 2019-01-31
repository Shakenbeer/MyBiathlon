package com.shakenbeer.biathlon.season.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.BOTTOM
import androidx.constraintlayout.widget.ConstraintSet.TOP
import com.shakenbeer.biathlon.BoilerplateApplication
import com.shakenbeer.biathlon.Const.teams
import com.shakenbeer.biathlon.R
import com.shakenbeer.biathlon.model.Event
import com.shakenbeer.biathlon.model.PastSeason
import com.shakenbeer.biathlon.season.SeasonComponent
import com.shakenbeer.biathlon.season.SeasonModule
import com.shakenbeer.biathlon.settings.UserSettings
import com.shakenbeer.biathlon.ui.BasePendingActivity
import com.shakenbeer.biathlon.ui.SingleLayoutAdapter
import kotlinx.android.synthetic.main.activity_season.*
import org.apache.commons.lang3.time.DateUtils
import java.util.Calendar.YEAR
import javax.inject.Inject


class SeasonActivity : BasePendingActivity<SeasonView, SeasonPresenter>(), SeasonView {

    override fun showEvents(events: List<Event>, anotherSeason: Boolean) {
        adapter.items = events.toMutableList()
        if (anotherSeason) {
            seasonRecyclerView.layoutManager?.scrollToPosition(0)
        }
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.localizedMessage, LENGTH_LONG).show()
    }

    override fun showPastSeason(pastSeason: PastSeason) {
        daysTextView.visibility = GONE
        timeTextView.visibility = GONE
        beforeTextView.visibility = GONE
        seasonTextView.visibility = VISIBLE

        ConstraintSet().run {
            clone(rootLayout)
            connect(R.id.seasonRecyclerView, TOP, R.id.seasonTextView, BOTTOM)
            applyTo(rootLayout)
        }

        val y1 = DateUtils.toCalendar(pastSeason.startDate).get(YEAR) % 100
        val y2 = DateUtils.toCalendar(pastSeason.endDate).get(YEAR) % 100
        seasonTextView.text = getString(R.string.past_season, y1.toString(), y2.toString())
    }

    override fun showTimer() {
        daysTextView.visibility = VISIBLE
        timeTextView.visibility = VISIBLE
        beforeTextView.visibility = VISIBLE
        seasonTextView.visibility = GONE

        ConstraintSet().run {
            clone(rootLayout)
            connect(R.id.seasonRecyclerView, TOP, R.id.beforeTextView, BOTTOM)
            applyTo(rootLayout)
        }
    }

    override fun tickTimer(days: Long, hours: Long, minutes: Long, seconds: Long, what: String) {
        if (days == 0L) {
            daysTextView.visibility = GONE
            space0.visibility = GONE
        } else {
            daysTextView.visibility = VISIBLE
            space0.visibility = VISIBLE
        }
        if (daysTextView.text != days.toString()) {
            daysTextView.text = days.toString()
        }
        timeTextView.text = getString(R.string.timer, hours, minutes, seconds)
        val before = getString(R.string.whats_next, what)
        if (beforeTextView.text != before) {
            beforeTextView.text = before
        }
    }

    @Inject
    lateinit var seasonPresenter: SeasonPresenter
    @Inject
    lateinit var userSettings: UserSettings
    @Inject
    lateinit var adapter: SingleLayoutAdapter

    @Suppress("DEPRECATION")
    private val seasonComponent: SeasonComponent by lazy {
        lastCustomNonConfigurationInstance as SeasonComponent?
                ?: (application as BoilerplateApplication)
                        .component
                        .plus(SeasonModule())
    }

    override fun pendingPresenter() = seasonPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        seasonComponent.inject(this)
        setContentView(R.layout.activity_season)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        seasonRecyclerView?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        seasonRecyclerView?.adapter = adapter
        updateBackground()
        if (savedInstanceState == null) {
            seasonPresenter.obtainEvents("1819", 1)
        } else {
            seasonPresenter.restore(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            seasonPresenter.destroy()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_choose_team) {
            showTeamDialog()
            return true
        }
        if (item.itemId == R.id.action_choose_season) {
            showSeasonDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showTeamDialog() {
        val builder = AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog)
        builder.setTitle(R.string.choose_team)
                .setItems(teams) { _, which ->
                    userSettings.setTeam(teams[which])
                    updateBackground()
                }
        builder.create().show()
    }

    private fun showSeasonDialog() {
        val builder = AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog)
        builder.setTitle(R.string.choose_season)
                .setItems(R.array.seasons) { _, which ->
                    val seasonId = resources.getStringArray(R.array.seasons)[which]
                            .replace("/", "")
                    seasonPresenter.obtainEvents(seasonId, 1)
                }
        builder.create().show()
    }

    private fun updateBackground() {
            rootLayout.setBackgroundResource(userSettings.getFlagRes())
    }

    @Suppress("OverridingDeprecatedMember")
    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return seasonComponent
    }
}

package com.alfredobejarano.srtracker.creategame.presenter

import com.alfredobejarano.srtracker.R
import com.alfredobejarano.srtracker.base.MAX_SR
import com.alfredobejarano.srtracker.base.MIN_SR
import com.alfredobejarano.srtracker.base.model.Match
import com.alfredobejarano.srtracker.base.presenter.BasePresenter
import com.alfredobejarano.srtracker.base.view.BaseView
import java.util.*

/**
 * Defines functions for storing a match in the local database.
 *
 * @author @AlfredoBejarano
 * @version 1.0
 * @since 2018-02-11
 */
class CreateGamePresenter(view: BaseView<Unit>) : BasePresenter<Unit>(view) {
    private lateinit var match: Match

    /**
     * Defines a match to be stored in the local database.
     * @param match The match to be stored.
     */
    fun storeMatch(match: Match) {
        view.displayLoadingView(displayView = true)
        val errorCode = validateMatchData(match = match)
        if (errorCode == 0) {
            this.match = match
            performDatabaseQuery()
        } else {
            view.displayError(errorCode)
        }
    }

    /**
     * Stores the defined match into the local database.
     */
    override fun query() {
        try {
            realmInstance?.executeTransaction { it.insertOrUpdate(match) } // Store the created match into the realm instance.
        } finally {
            closeRealmInstance()
        }
    }

    /**
     * Uses a Calendar instance to retrieve the current date.
     * @return Current date as dd-MM-yyyy h:mm
     */
    fun getDate(): String {
        val calendar = Calendar.getInstance()
        return "${calendar.get(Calendar.DAY_OF_MONTH)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)} ${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
    }

    /**
     * Checks that the values introduced for storing
     * a match are correct.
     */
    private fun validateMatchData(match: Match): Int {
        return when {
            match.heroes.isEmpty() -> R.string.please_select_a_hero
            match.sr < MIN_SR -> R.string.your_sr_cant_be_below_zero
            match.sr > MAX_SR -> R.string.your_sr_cant_be_above_five_thounsand
            else -> 0
        }
    }
}
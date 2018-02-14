package com.alfredobejarano.srtracker.creategame.presenter

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
        this.match = match
        performDatabaseQuery()
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
     * @return Current date as dd-mm-yy h:mm
     */
    fun getDate(): String {
        val calendar = Calendar.getInstance()
        return "${calendar.get(Calendar.DAY_OF_MONTH)}-${calendar.get(Calendar.MONTH)}-${calendar.get(Calendar.YEAR)} ${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
    }
}
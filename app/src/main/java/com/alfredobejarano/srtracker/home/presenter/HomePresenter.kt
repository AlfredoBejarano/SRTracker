package com.alfredobejarano.srtracker.home.presenter

import com.alfredobejarano.srtracker.base.model.Match
import com.alfredobejarano.srtracker.base.presenter.BasePresenter
import com.alfredobejarano.srtracker.base.view.BaseView
import io.realm.Realm


/**
 * Presenter for the home activity, it queries through the realm database
 * and reports the found data to the activity.
 *
 * @author @AlfredoBejarano
 * @version 1.0
 * @since 2018-02-11
 */
class HomePresenter(view: BaseView<MutableList<Match>>) : BasePresenter<MutableList<Match>>(view = view) {
    /**
     * Acces the Realm instance and retrieves all the stored matches.
     * @return All the matches stored locally found in the local database.
     */
    override fun query(): MutableList<Match> {
        val transaction = GameListTransaction() // Create a transaction for retrieving the local stored matches.
        if (realmInstance?.isClosed!!) {
            realmInstance = Realm.getDefaultInstance()
        }
        realmInstance.use { it?.executeTransaction(transaction) } // Execute the transaction.
        return transaction.matches // Return the found matches as a list.
    }

    fun deleteAllRecords() {
        view.displayLoadingView(displayView = true)
        if (realmInstance?.isClosed!!) {
            realmInstance = Realm.getDefaultInstance()
        }
        try {
            realmInstance!!.executeTransaction {
                it.delete(Match::class.java)
            }
        } finally {
            closeRealmInstance()
            performDatabaseQuery()
        }
    }
}
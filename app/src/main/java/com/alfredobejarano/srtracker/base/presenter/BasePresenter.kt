package com.alfredobejarano.srtracker.base.presenter

import com.alfredobejarano.srtracker.base.view.BaseView
import io.realm.Realm

/**
 * Defines a base presenter class, it initializes a Realm instance
 * and defines a query() function to use it.
 *
 * @author @AlfredoBejarano
 * @version 1.0
 * @since 2018-02-11
 */
abstract class BasePresenter<DTO>(val view: BaseView<DTO>) {
    protected var realmInstance: Realm? = Realm.getDefaultInstance()

    /**
     * Closes the realm connection if its not null.
     */
    fun closeRealmInstance() {
        realmInstance?.close()
    }

    /**
     * This method defines the instructions for retrieving data from the Realm database.
     * @return The results from the database query.
     */
    abstract fun query(): DTO

    /**
     * Executes the query() functions and then reports the data into the view.
     */
    open fun performDatabaseQuery() {
        view.displayLoadingView(displayView = true)
        val data = query() // Execute the query() function and assign its result to the data variable.
        view.setup(data) // Execute the data send into the main thread.
        view.displayLoadingView(displayView = false)
    }
}
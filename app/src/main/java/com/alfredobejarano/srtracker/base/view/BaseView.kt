package com.alfredobejarano.srtracker.base.view

import android.content.Context
import android.support.annotation.StringRes

/**
 * Defines methods available for a view.
 *
 * @author @AlfredoBejarano
 * @version 1.0
 * @since 2018-02-11
 */
interface BaseView<in DTO> {
    /**
     * Defines a method for initializeing listeners or callbacks in views.
     */
    fun initializeView()

    /**
     * Defines a method to set a presenter in the view.
     */
    fun attachPresenter()

    /**
     * Defines a method that receives the processed data from
     * a presenter.
     */
    fun setup(data: DTO)

    /**
     * Defines a method for displaying an error to the user.
     */
    fun displayError(@StringRes message: Int)

    /**
     * Defines a method for displaying a message to the user.
     */
    fun displayMessage(message: String)

    /**
     * Defines a method to show or hide (true or false) the loading view.
     */
    fun displayLoadingView(displayView: Boolean)

    /**
     * Returns the context instance of the View implementation.
     */
    fun getContext(): Context
}
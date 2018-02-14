package com.alfredobejarano.srtracker.base.view

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import com.alfredobejarano.srtracker.R
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.view_loading.*
import kotlinx.android.synthetic.main.view_toolbar.*

/**
 * Defines the template for an Activity in this app.
 *
 * All activities root layout should be a ConstraintLayout.
 *
 * @version 1.0
 * @author @AlfredoBejarano
 * @since 31/01/2018 at 05:17 PM
 */
abstract class BaseActivity<in DTO> : AppCompatActivity(), BaseView<DTO> {

    /**
     * Initializes this class implementation.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base) // Sets the base content view.
        attachPresenter() // Attaches the presenter for this class implementation.
        setSupportActionBar(toolbar)
        setupActionBar(supportActionBar) // Set the action bar's title and back behaviour.
        LayoutInflater.from(this).inflate(getLayoutId(), activity_content, true) // Attach this class implementation root layout inside the ScrollView.
        initializeView() // At the end, calls all the views initializations like listeners and such.
    }

    /**
     * Sets the activity title bar.
     */
    open fun setupActionBar(actionBar: ActionBar?) {
        if (actionBar != null && toolbar_title != null) {
            toolbar_title.setText(getActivityTitle())
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    /**
     * Returns the title for this activity.
     */
    abstract fun getActivityTitle(): Int

    /**
     * Override with the Activity layout ID for the implementation of this class.
     *
     * @return The layout ID for this activity root.
     */
    abstract fun getLayoutId(): Int

    /**
     * Displays a Snackbar displaying an User friendly error
     * while providing fixing solutions if there are available.
     *
     * @param message The String ID resource for the message.
     */
    override fun displayError(@StringRes message: Int) {
        displayLoadingView(displayView = false)
        createSnackbar(text = getString(message))
    }

    /**
     * Displays a Snackbar to the user with a text.
     *
     * @param message Text for the toast.
     */
    override fun displayMessage(message: String) {
        createSnackbar(text = message)
    }

    /**
     * Displays a Snackbar in the activity.
     */
    private fun createSnackbar(text: String) {
        val view = findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    /**
     * Shows or hides the loading view.
     */
    override fun displayLoadingView(displayView: Boolean) {
        loading_view.let { it.visibility = if (displayView) View.VISIBLE else View.GONE }
    }

    /**
     * Sets the behavior of the back icon in the toolbar to go back in the activity stack.
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return true
    }

    /**
     * Returns the context of this view.
     */
    override fun getContext(): Context = this
}
package com.alfredobejarano.srtracker.home.view

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.alfredobejarano.srtracker.R
import com.alfredobejarano.srtracker.base.model.Match
import com.alfredobejarano.srtracker.base.view.BaseActivity
import com.alfredobejarano.srtracker.creategame.view.CreateGameActivity
import com.alfredobejarano.srtracker.home.presenter.GameListAdapter
import com.alfredobejarano.srtracker.home.presenter.HomePresenter
import kotlinx.android.synthetic.main.activity_home.*

/**
 * This is the first activity that the users sees after the SplashActivity.
 * Displays a list of matches.
 *
 * @author @AlfredoBejarano
 * @version 1.0
 * @since 2018-02-11
 */
class HomeActivity : BaseActivity<MutableList<Match>>() {
    private lateinit var presenter: HomePresenter
    private val adapter = GameListAdapter(listOf())

    /**
     * Sets listener and callbacks for views.
     */
    override fun initializeView() {
        hm_game_list.layoutManager = LinearLayoutManager(this) // Sets a layout manager for the list.
        hm_game_list.adapter = adapter // Adds an adapter for the game list to render the matches data.
    }

    /**
     * Inflates the home menu into this activity.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home, menu)
        return true
    }

    /**
     * Reacts to the action selected on the menu.
     */
    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.add_game -> { // Go to add activity.
            startActivity(Intent(this, CreateGameActivity::class.java)) // Opens the CreateGameActivity to add a game to the list.
            true
        }
        R.id.delete_game -> { // Display an alert dialog.
            AlertDialog.Builder(this)
                    .setTitle(R.string.delete_all_games) // Set the dialog title.
                    .setMessage(R.string.are_you_sure_to_delete_games) // Sets the dialog message.
                    .setPositiveButton(R.string.yes, { _, _ -> presenter.deleteAllRecords() }) // Call the deleteAllRecords function in the presenter if "yes" is pressed.
                    .setNegativeButton(R.string.no, { dialogInterface, _ -> dialogInterface.dismiss() }) // Dismiss the alert dialog.
                    .show() // Display the dialog.
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * Binds a BasePresenter implementation for this Activity.
     */
    override fun attachPresenter() {
        presenter = HomePresenter(view = this)
    }

    /**
     * Renders the data retrieved from the presenter.
     * @param data The list of matches.
     */
    override fun setup(data: MutableList<Match>) {
        if (data.isEmpty()) {
            hm_game_list.visibility = View.GONE // Hide the matches list.
            hm_no_games_label.visibility = View.VISIBLE // Show a label indicating that no matches were found.
        } else {
            hm_game_list.visibility = View.VISIBLE // Show the matches list.
            hm_no_games_label.visibility = View.GONE // Hide a label indicating that no matches were found.
            adapter.swapGames(data) // Report the new matches data.
        }
    }

    /**
     * @return The title for this activity.
     */
    override fun getActivityTitle(): Int = R.string.app_name

    /**
     * @return The layout ID for this activity content.
     */
    override fun getLayoutId(): Int = R.layout.activity_home

    /**
     * Update the game list when the activity resumes.
     */
    override fun onResume() {
        super.onResume()
        presenter.performDatabaseQuery()
    }
}

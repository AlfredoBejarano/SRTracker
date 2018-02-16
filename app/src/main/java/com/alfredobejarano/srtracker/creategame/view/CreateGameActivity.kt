package com.alfredobejarano.srtracker.creategame.view

import android.view.animation.AnimationUtils
import com.alfredobejarano.srtracker.R
import com.alfredobejarano.srtracker.base.HERO_NAMES
import com.alfredobejarano.srtracker.base.MAP_ANUBIS
import com.alfredobejarano.srtracker.base.MAP_NAMES
import com.alfredobejarano.srtracker.base.model.Match
import com.alfredobejarano.srtracker.base.safeString
import com.alfredobejarano.srtracker.base.view.BaseActivity
import com.alfredobejarano.srtracker.creategame.presenter.CreateGamePresenter
import com.alfredobejarano.srtracker.heropicker.HeroPicker
import com.alfredobejarano.srtracker.mappicker.MapPicker
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_create_game.*

class CreateGameActivity : BaseActivity<Unit>(), HeroPicker.OnHeroChangedListener, MapPicker.OnMapChangedListener {

    /**
     * Reacts when a map has been changed and prints it name.
     */
    override fun onMapChanged(mapId: Int) = cg_map_name.setText(MAP_NAMES[mapId])

    /**
     * Reacts when a hero has been selected and prints their names.
     */
    override fun onHeroChanged(heroIds: MutableList<Int>) = when {
        heroIds.size == 1 -> cg_selected_hero.setText(HERO_NAMES[heroIds[0]])
        heroIds.isEmpty() -> cg_selected_hero.setText(R.string.no_hero_selected)
        else -> cg_selected_hero.setText(R.string.multiple_heroes)
    }

    private lateinit var presenter: CreateGamePresenter

    /**
     * Sets the click onMapChangedListener for the save button
     * and a hero changed onMapChangedListener to the hero picker.
     */
    override fun initializeView() {
        cg_map_picker.onMapChangedListener = this
        cg_map_picker.selectMap(MAP_ANUBIS)
        cg_save.setOnClickListener { sendMatchToPresenter() }
        cg_hero_picker.heroChangedListener = this // Sets the hero changed onMapChangedListener for the hero picker view.
    }

    /**
     * Creates a Match instance ands sends it to the presenter
     * for validation and local persistence.
     */
    private fun sendMatchToPresenter() {
        val heroes = RealmList<Int>() // Creates a RealmList for storing the hero names.
        heroes.addAll(cg_hero_picker.selectedHeroes) // Adds all the selected heroes IDs to the RealmList.
        presenter.storeMatch(Match(
                sr = safeString(cg_sr_field.text).toInt(),
                hero = heroes,
                map = cg_map_picker.selectedMap,
                date = presenter.getDate())
        ) // Store the match into the local database.
    }

    /**
     * Assigns an implementation of the
     * BasePresenter class to this activity.
     */
    override fun attachPresenter() {
        presenter = CreateGamePresenter(this)
        displayLoadingView(displayView = false)
    }

    /**
     * Closes the activity when a Match has been successfully added.
     */
    override fun setup(data: Unit) {
        finish()
    }

    /**
     * @return The String ID for this activity toolbar.
     */
    override fun getActivityTitle(): Int = R.string.create_game

    /**
     * @return The layout id for this activity.
     */
    override fun getLayoutId(): Int = R.layout.activity_create_game

    /**
     * Displays a SnackBar with an error message.
     * @param message The error message id string.
     */
    override fun displayError(message: Int) {
        super.displayError(message)
        val view = when (message) { // Sets a view for animate when an error happens.
            R.string.please_select_a_hero -> cg_hero_picker
            R.string.please_enter_a_map_name -> cg_map_picker
            else -> cg_sr_field
        }
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error)) // Animate the view.
    }
}

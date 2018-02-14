package com.alfredobejarano.srtracker.creategame.view

import com.alfredobejarano.srtracker.R
import com.alfredobejarano.srtracker.base.HERO_NAMES
import com.alfredobejarano.srtracker.base.model.Match
import com.alfredobejarano.srtracker.base.view.BaseActivity
import com.alfredobejarano.srtracker.creategame.presenter.CreateGamePresenter
import com.alfredobejarano.srtracker.heropicker.HeroPicker
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_create_game.*

class CreateGameActivity : BaseActivity<Unit>(), HeroPicker.OnHeroChangedListener {

    /**
     * Reacts when a hero has changed and prints their names.
     */
    override fun onHeroChanged(heroIds: MutableList<Int>) {
        if (heroIds.size == 1) {
            cg_selected_hero.setText(HERO_NAMES[heroIds[0]])
        } else {
            cg_selected_hero.setText(R.string.multiple_heroes)
        }
    }

    private lateinit var presenter: CreateGamePresenter

    /**
     * Sets the click listener for the save button.
     */
    override fun initializeView() {
        cg_save.setOnClickListener {
            val heroes = RealmList<Int>() // Creates a RealmList for storing the hero names.
            heroes.addAll(cg_hero_picker.selectedHeroes) // Adds all the selected heroes IDs to the RealmList.
            presenter.storeMatch(Match(sr = cg_sr_field.text.toString().toInt(), hero = heroes, map = cg_map_field.text.toString(), date = presenter.getDate())) // Store the match into the local database.
        }
    }

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

    override fun getActivityTitle(): Int = R.string.create_game

    /**
     * @return The layout id for this activity.
     */
    override fun getLayoutId(): Int = R.layout.activity_create_game
}

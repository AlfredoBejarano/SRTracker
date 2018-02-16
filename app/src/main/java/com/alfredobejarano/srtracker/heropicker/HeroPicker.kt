package com.alfredobejarano.srtracker.heropicker

import android.content.Context
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import com.alfredobejarano.srtracker.R
import com.alfredobejarano.srtracker.base.HEROES
import com.alfredobejarano.srtracker.base.HERO_ICONS
import com.alfredobejarano.srtracker.base.getDP

/**
 * Custom View that displays all the heroes available in the game,
 * allows choosing multiple heroes.
 *
 * @author @AlfredoBejarano
 * @version 1.0
 * @since 2018-02-11
 */
class HeroPicker(context: Context, attributeSet: AttributeSet) : HorizontalScrollView(context, attributeSet) {
    var selectedHeroes: MutableList<Int> = mutableListOf()
    var heroChangedListener: OnHeroChangedListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_hero_picker, this, true)
        val heroContainer = (getChildAt(0) as HorizontalScrollView).getChildAt(0) as LinearLayout // Get the heroes container.
        for (i in 0 until HEROES) {
            heroContainer.addView(buildHeroImageView(position = i)) // Add a heroes icon to the linear layout.
        }
    }

    /**
     * Builds a Hero Icon with a given heroes constant.
     * @return ImageView with the heroes icon.
     */
    private fun buildHeroImageView(position: Int): ImageView {
        val padding = getDP(value = 8, resources = resources) // Padding value for the image view.
        val hero = ImageView(context) // ImageView for the heroes.
        val imageParams = LayoutParams(getDP(value = 48, resources = resources), getDP(value = 48, resources = resources)) // Set the size of the image to 24 dp.
        imageParams.setMargins(getDP(value = 8, resources = resources), getDP(value = 8, resources = resources), getDP(value = 8, resources = resources), getDP(value = 8, resources = resources)) // Set the margins of the image to 4dp.
        hero.layoutParams = imageParams // Set the layout params for the image view.
        hero.setPadding(padding, padding, padding, padding) // Set the padding for the background resource.
        hero.setImageResource(HERO_ICONS[position]) // Set the heroes icon.
        hero.adjustViewBounds = true // Make the image resource adjust to the view size.
        hero.background = ContextCompat.getDrawable(context, R.drawable.bk_hero_icon) // Set the heroes icon background.
        hero.setOnClickListener { selectHero(position) } // Set the selected heroes when clicking the icon.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            hero.elevation = getDP(value = 2, resources = resources).toFloat() // Set the heroes icon elevation in Lollipop and above devices.
        }
        return hero
    }

    /**
     * Selects a heroes in the given heroId and un-selects the rest.
     */
    private fun selectHero(heroId: Int) {
        val heroContainer = (getChildAt(0) as HorizontalScrollView).getChildAt(0) as LinearLayout // Get the heroes container.
        var hero: ImageView
        for (i in 0 until heroContainer.childCount) { // iterate through all the hero items to add / remove one from the list.
            hero = heroContainer.getChildAt(i) as ImageView // Get the current heroes portrait.
            if (i == heroId) {
                if (selectedHeroes.contains(heroId)) { // if the list already contains the hero, remove it.
                    selectedHeroes.removeAt(selectedHeroes.indexOf(heroId)) // Add the selected hero to the hero list.
                    hero.setBackgroundResource(R.drawable.bk_hero_icon) // Change the background of the selected heroes.
                } else {
                    selectedHeroes.add(heroId) // Add the selected hero to the hero list.
                    hero.setBackgroundResource(R.drawable.bk_hero_icon_selected) // Change the background of the selected heroes.
                }
            }
        }
        if (heroChangedListener != null) {
            heroChangedListener!!.onHeroChanged(selectedHeroes) // Report the heroes changed to the onMapChangedListener if not null.
        }
    }

    interface OnHeroChangedListener {
        fun onHeroChanged(heroIds: MutableList<Int>)
    }
}
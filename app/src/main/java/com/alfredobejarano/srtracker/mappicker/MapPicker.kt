package com.alfredobejarano.srtracker.mappicker

import android.content.Context
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import com.alfredobejarano.srtracker.R
import com.alfredobejarano.srtracker.base.MAPS
import com.alfredobejarano.srtracker.base.MAP_ANUBIS
import com.alfredobejarano.srtracker.base.MAP_ICONS
import com.alfredobejarano.srtracker.base.getDP

/**
 * Custom View that displays all the heroes available in the game,
 * allows choosing multiple heroes.
 *
 * @author @AlfredoBejarano
 * @version 1.0
 * @since 2018-02-11
 */
class MapPicker(context: Context, attributeSet: AttributeSet) : HorizontalScrollView(context, attributeSet) {
    var selectedMap: Int = 0
    var onMapChangedListener: OnMapChangedListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_hero_picker, this, true)
        val mapContainer = (getChildAt(0) as HorizontalScrollView).getChildAt(0) as LinearLayout // Get the heroes container.
        for (i in 0 until MAPS) {
            mapContainer.addView(buildMapIcon(position = i)) // Add a heroes icon to the linear layout.
        }
    }

    /**
     * Builds a Hero Icon with a given heroes constant.
     * @return ImageView with the heroes icon.
     */
    private fun buildMapIcon(position: Int): ImageView {
        val mapIcon = ImageView(context) // ImageView for the heroes.
        val imageParams = LayoutParams(getDP(value = 70, resources = resources), getDP(value = 38, resources = resources)) // Set the size of the image to 70 dp wide and 38 dp height.
        imageParams.setMargins(getDP(value = 8, resources = resources), getDP(value = 8, resources = resources), getDP(value = 8, resources = resources), getDP(value = 8, resources = resources)) // Set the margins of the image to 8dp.
        mapIcon.layoutParams = imageParams // Set the layout params for the image view.
        mapIcon.setImageResource(R.drawable.bk_map) // Set the unselected drawable for the map.
        mapIcon.adjustViewBounds = true // Make the image resource adjust to the view size.
        mapIcon.background = ContextCompat.getDrawable(context, MAP_ICONS[position]) // Set the map icon in the background.
        mapIcon.setOnClickListener { selectMap(position) } // Set the selected heroes when clicking the icon.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mapIcon.elevation = getDP(value = 2, resources = resources).toFloat() // Set the heroes icon elevation in Lollipop and above devices.
        }
        return mapIcon
    }

    /**
     * Selects a heroes in the given heroId and un-selects the rest.
     */
    fun selectMap(mapId: Int) {
        val mapContainer = (getChildAt(0) as HorizontalScrollView).getChildAt(0) as LinearLayout // Get the map container.
        var mapIcon: ImageView
        for (i in 0 until mapContainer.childCount) { // iterate through all the map items to add / remove one from the list.
            mapIcon = mapContainer.getChildAt(i) as ImageView // Get the current map portrait.
            if (i == mapId) {
                mapIcon.setImageResource(R.drawable.bk_map_selected) // Set the selected foreground if the position matches an map Id.
            } else {
                mapIcon.setImageResource(R.drawable.bk_map) // Set the unselected foreground for the rest of images.
            }
        }
        selectedMap = mapId // Update the selected map value.
        if (onMapChangedListener != null) {
            onMapChangedListener!!.onMapChanged(selectedMap) // Report the map changed to the onMapChangedListener if not null.
        }
    }

    interface OnMapChangedListener {
        fun onMapChanged(mapId: Int)
    }
}
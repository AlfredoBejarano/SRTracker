package com.alfredobejarano.srtracker.home.view

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alfredobejarano.srtracker.R
import com.alfredobejarano.srtracker.base.HERO_ICONS
import com.alfredobejarano.srtracker.base.model.Match
import java.text.SimpleDateFormat
import java.util.*

/**
 * Defines a Match element in the RecyclerView list.
 *
 * @author @AlfredoBejarano
 * @version 1.0
 * @since 2018-02-11
 */
class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val srField: TextView = itemView.findViewById(R.id.gm_sr)
    private val mapField: TextView = itemView.findViewById(R.id.gm_map)
    private val heroIcon: ImageView = itemView.findViewById(R.id.gm_hero)
    private val dateField: TextView = itemView.findViewById(R.id.gm_date)

    /**
     * Renders the match data into the item.
     */
    fun render(prevMatch: Match, match: Match) {
        val srDifference = match.sr - prevMatch.sr // Difference between last and next prevMatch SR.
        val color = ContextCompat.getColor(itemView.context, if (srDifference > 0) R.color.colorGreen else R.color.colorRed) // Set the color depending if win or loss of SR.

        mapField.text = match.map // Set the map name.
        heroIcon.setImageResource(HERO_ICONS[match.heroes[0]!!]) // Set the heroes icon.
        if (srDifference == 0) {
            srField.text = String.format(Locale.getDefault(), itemView.context.getString(R.string.match_sr), match.sr) // Draw the SR if a match difference is zero.
        } else {
            srField.setTextColor(color) // Set the text color for losing SR.
            srField.text = String.format(Locale.getDefault(), itemView.context.getString(R.string.match_sr_difference), match.sr, srDifference) // Draws the current SR and the SR difference.
        }

        dateField.text = parseDate(date = match.date)// Draw the date.
    }

    /**
     * Parses a date from dd-MM-yyyy H:mm format
     * to MMMM, dd yyyy. h:mm a format.
     */
    private fun parseDate(date: String): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy H:mm", Locale.getDefault())
        val stringAsDate = dateFormat.parse(date)
        return SimpleDateFormat("MMMM, dd yyyy. h:mm a", Locale.getDefault()).format(stringAsDate)
    }
}
package com.alfredobejarano.srtracker.home.presenter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alfredobejarano.srtracker.R
import com.alfredobejarano.srtracker.base.model.Match
import com.alfredobejarano.srtracker.home.view.MatchViewHolder

/**
 * This class creates items from a game list and renders the data into them.
 *
 * @author @AlfredoBejarano
 * @version 1.0
 * @since 2018-02-11
 */
class GameListAdapter(private var matches: List<Match>) : RecyclerView.Adapter<MatchViewHolder>() {
    /**
     * Binds a created ViewHolder instance to the RecyclerView list.
     */
    override fun onBindViewHolder(holder: MatchViewHolder?, position: Int) {
        val prevGame = if (position - 1 <= 0) 0 else position - 1
        holder?.render(prevMatch = matches[prevGame], match = matches[position])
    }

    /**
     * Creates a new ViewHolder item.
     * @return Created Match ViewHolder instance.
     */
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MatchViewHolder = MatchViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_game, parent, false))

    /**
     * @return Quantity of view holder items to be created.
     */
    override fun getItemCount(): Int = matches.size

    /**
     * Changes the matches list and
     * reports the changes to the recycler.
     */
    fun swapGames(matches: List<Match>) {
        this.matches = matches
        notifyDataSetChanged()
    }

    /**
     * Detects when a game is the last on the list so it
     * can render the item without the separator line.
     */
    override fun getItemViewType(position: Int): Int = if (position == itemCount - 1) R.layout.item_game_last else R.layout.item_game
}
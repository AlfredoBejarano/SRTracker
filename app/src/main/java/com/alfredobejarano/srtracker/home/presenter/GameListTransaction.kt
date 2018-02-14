package com.alfredobejarano.srtracker.home.presenter

import com.alfredobejarano.srtracker.base.model.Match
import io.realm.Realm

/**
 * Defines a realm transaction to retrieve the local stored matches.
 *
 * @author @AlfredoBejarano
 * @version 1.0
 * @since 2018-02-11
 */
class GameListTransaction : Realm.Transaction {
    var matches: MutableList<Match> = mutableListOf()
    override fun execute(realm: Realm?) {
        val foundGames = realm?.where(Match::class.java)?.findAll()
        if (foundGames != null) {
            (0 until foundGames.size)
                    .mapNotNull { foundGames[it] }
                    .forEach { matches.add(realm.copyFromRealm(it)) } // Add the game found to the list.
        }
    }
}
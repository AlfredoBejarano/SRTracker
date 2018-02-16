package com.alfredobejarano.srtracker.base.model

import io.realm.RealmList
import io.realm.RealmObject

/**
 * Defines a Match object containing the heroes played, the map played
 * and the SR after the match.
 *
 * @author @AlfredoBejarano
 * @version 1.0
 * @since 2018-02-11
 */
open class Match() : RealmObject() {
    var sr = 0
    var map = 0
    var heroes: RealmList<Int> = RealmList()
    var date = ""

    constructor(sr: Int, hero: RealmList<Int>, map: Int, date: String) : this() {
        this.sr = sr
        this.map = map
        this.heroes = hero
        this.date = date
    }
}
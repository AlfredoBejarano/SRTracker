package com.alfredobejarano.srtracker

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * @author @AlfredoBejarano
 * @version 1.0
 * @since 2018-02-11
 */
class SRTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(applicationContext)
        val realmConfiguration = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}
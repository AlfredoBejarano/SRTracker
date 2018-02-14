package com.alfredobejarano.srtracker

import android.app.backup.BackupAgentHelper
import android.app.backup.BackupDataInput
import android.app.backup.BackupDataOutput
import android.app.backup.FileBackupHelper
import android.os.ParcelFileDescriptor
import io.realm.Realm

/**
 * @author @AlfredoBejarano
 * @version 1.0
 * @since 2018-02-11
 */
class BackupAgent : BackupAgentHelper() {
    override fun onCreate() {
        val file = Realm.getDefaultInstance().getConfiguration().realmFileName
        val hosts = FileBackupHelper(this, file)
        addHelper(file, hosts)
    }

    override fun onBackup(oldState: ParcelFileDescriptor?, data: BackupDataOutput?, newState: ParcelFileDescriptor?) {
        synchronized(BackupAgent::class.java) {
            super.onBackup(oldState, data, newState)
        }
    }

    override fun onRestore(data: BackupDataInput?, appVersionCode: Int, newState: ParcelFileDescriptor?) {
        synchronized(BackupAgent::class.java) {
            super.onRestore(data, appVersionCode, newState)
        }
    }
}
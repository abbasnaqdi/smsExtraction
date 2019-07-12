package com.oky2abbas.sms2html.backup

import io.reactivex.Observable

interface BackupContract {
    interface Model {
        fun saveHTML(htmlString: String, name: String): Observable<Boolean?>?
    }

    interface Presenter {
        fun saveBackup(backupEnum: BackupEnum)
    }

    interface View {
        fun eventSaved()
        fun eventMessage(msg: String)
    }
}
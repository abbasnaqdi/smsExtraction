package com.oky2abbas.sms2html.backup

import android.content.Context
import com.oky2abbas.sms2html.helper.FileHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileWriter
import java.io.IOException

class BackupModel(private val context: Context) : BackupContract.Model {
    private var fileHelper: FileHelper? = null

    init {
        if (fileHelper == null) fileHelper = FileHelper(context)
    }

    override fun saveHTML(htmlString: String, name: String): Observable<Boolean?>? {
        return Observable.fromCallable {
            save(htmlString, name)
        }.subscribeOn(Schedulers.io())
    }

    private fun save(body: String, name: String): Boolean {
        try {
            val root = File(fileHelper?.getPath())
            if (!root.exists()) {
                root.mkdirs()
            }

            val html = File(root, "$name.html")
            val writer = FileWriter(html)
            writer.append(body)
            writer.flush()
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

        return true
    }
}
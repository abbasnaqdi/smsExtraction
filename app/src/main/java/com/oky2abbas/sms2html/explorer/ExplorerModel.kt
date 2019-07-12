package com.oky2abbas.sms2html.explorer

import android.content.Context
import com.oky2abbas.sms2html.helper.FileHelper
import java.io.File

class ExplorerModel(val context: Context) : ExplorerContract.Model {
    private var fileHelper: FileHelper? = null

    init {
        if (fileHelper == null) fileHelper = FileHelper(context)
    }

    override fun getBackups(): ArrayList<File> {
        val arrayStruct = arrayListOf<File>()
        val files = fileHelper?.getFiles()

        files?.forEach {
            if (it.isFile)
                arrayStruct.add(it)
        }

        return arrayStruct
    }
}
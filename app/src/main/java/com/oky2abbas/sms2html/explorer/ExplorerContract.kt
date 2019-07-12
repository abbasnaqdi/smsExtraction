package com.oky2abbas.sms2html.explorer

import java.io.File

interface ExplorerContract {
    interface Model {
        fun getBackups(): ArrayList<File>
    }

    interface Presenter {
        fun getBackups()
    }

    interface View {
        fun eventFound(arrayStruct: ArrayList<ExplorerStruct>)
        fun eventNotFound()
        fun eventMessage(msg: String)
    }
}
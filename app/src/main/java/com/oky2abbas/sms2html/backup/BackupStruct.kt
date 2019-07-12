package com.oky2abbas.sms2html.backup

data class BackupStruct(
    var name: String? = null,
    var address: String? = null,
    var body: ArrayList<HashMap<Int, String>> = arrayListOf()
)
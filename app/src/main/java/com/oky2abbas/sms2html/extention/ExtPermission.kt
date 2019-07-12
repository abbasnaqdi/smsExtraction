package com.oky2abbas.sms2html.extention

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Context.isPermission(): Boolean {
    if (Build.VERSION.SDK_INT < 23) return true

    val permissions = arrayOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val isSMS =
        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
    val isContacts =
        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
    val isStorage = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    if (!isSMS || !isContacts || !isStorage) ActivityCompat.requestPermissions(this as Activity, permissions, 0)

    return isSMS && isContacts && isStorage
}
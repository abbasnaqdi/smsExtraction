package com.oky2abbas.sms2html.helper

import androidx.fragment.app.FragmentActivity
import com.irozon.sneaker.Sneaker
import com.oky2abbas.sms2html.R
import com.oky2abbas.sms2html.helper.SneakerHelper.SneakerModel.*

class SneakerHelper {
    enum class SneakerModel {
        SUCCESSFUL, UNSUCCESSFUL,
        WARNING, ERROR
    }

    fun showMessage(activity: FragmentActivity, message: String, model: SneakerModel) {
        Sneaker.with(activity)
            .setTitle(message, R.color.colorWhite)
            .setIcon(getIcon(model), false)
            .setDuration(3000)
            .autoHide(true)
            .setHeight(100)
            .sneak(getColor(model))
    }

    private fun getIcon(model: SneakerModel): Int {
        return when (model) {
            SUCCESSFUL -> R.drawable.ico_successful
            UNSUCCESSFUL -> R.drawable.ico_unsuccessful
            WARNING -> R.drawable.ico_warnings
            ERROR -> R.drawable.ico_error
        }
    }

    private fun getColor(model: SneakerModel): Int {
        return when (model) {
            SUCCESSFUL -> R.color.colorPrimaryDark
            UNSUCCESSFUL -> R.color.colorBlack
            WARNING -> R.color.colorOrange
            ERROR -> R.color.colorHepatic
        }
    }
}
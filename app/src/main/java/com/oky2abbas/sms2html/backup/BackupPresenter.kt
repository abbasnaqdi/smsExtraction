package com.oky2abbas.sms2html.backup

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.BaseColumns
import android.provider.ContactsContract
import com.oky2abbas.sms2html.R
import com.oky2abbas.sms2html.bus.RefreshBus
import com.oky2abbas.sms2html.helper.HTMLBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*


class BackupPresenter(val view: BackupView) : BackupContract.Presenter {
    private var context: Context? = null
    private var modelLayer: BackupModel? = null
    private var viewLayer: WeakReference<BackupContract.View>? = null
    private var htmlBuilder: HTMLBuilder? = null

    init {
        if (context == null) context = WeakReference(view).get()?.context

        context?.let {
            if (modelLayer == null) modelLayer = BackupModel(it)
            if (viewLayer == null) viewLayer = WeakReference(view)
            if (htmlBuilder == null) htmlBuilder = HTMLBuilder()
        }
    }

    private fun getView(): BackupContract.View? {
        return viewLayer?.get()
    }

    override fun saveBackup(backupEnum: BackupEnum) {
        Observable.fromCallable {
            readAllSMS()
        }.subscribeOn(Schedulers.newThread()).subscribe {
            Observable.fromCallable {
                mergeSortSMS(it, backupEnum)
            }.subscribeOn(Schedulers.newThread()).subscribe {
                Observable.fromCallable {
                    htmlBuilder?.makeHTML(it)
                }.subscribeOn(Schedulers.newThread()).subscribe {
                    modelLayer?.saveHTML(it!!, makeName())?.observeOn(AndroidSchedulers.mainThread())?.subscribe {
                        if (it!!) {
                            getView()?.eventSaved()
                            EventBus.getDefault().postSticky(RefreshBus())
                        } else getView()?.eventMessage(context!!.getString(R.string.strErrorSave))
                    }
                }
            }
        }
    }

    @SuppressLint("Recycle")
    private fun readAllSMS(): ArrayList<HashMap<String, HashMap<Int, String>>> {

        val inboxURI = Uri.parse("content://sms2html")
        val reqCols = arrayOf("address", "type", "body")
        val c = context?.contentResolver?.query(inboxURI, reqCols, null, null, null)

        val arrayMap = arrayListOf<HashMap<String, HashMap<Int, String>>>()
        if (c?.moveToFirst()!!) {
            do {
                val map = hashMapOf<String, HashMap<Int, String>>()
                val m = hashMapOf<Int, String>()

                m[c.getInt(1)] = c.getString(2)
                map[c.getString(0)] = m

                arrayMap.add(map)

            } while (c.moveToNext())
        }

        return arrayMap
    }

    private fun mergeSortSMS(
        arrayMap: ArrayList<HashMap<String, HashMap<Int,
                String>>>, backupEnum: BackupEnum
    ): ArrayList<BackupStruct> {

        val arraySMSModel: ArrayList<BackupStruct> = arrayListOf()

        arrayMap.forEach {
            it.forEach {
                if (!isExistAddress(it.key, arraySMSModel)) {

                    val smsModel = BackupStruct(name = getNameByAddress(it.key), address = it.key)

                    arrayMap.forEach {
                        it.forEach {
                            if (it.key == smsModel.address) {

                                val size = it.value.size
                                var i = 0

                                it.value.forEach {
                                    val m = hashMapOf<Int, String>()
                                    m[it.key] = it.value

                                    if (backupEnum == BackupEnum.ASC) {
                                        i++
                                        smsModel.body.add((size - i), m)
                                    } else smsModel.body.add(m)
                                }
                            }
                        }
                    }

                    arraySMSModel.add(smsModel)
                }
            }
        }

        return arraySMSModel
    }

    private fun isExistAddress(address: String, arraySMSModel: ArrayList<BackupStruct>): Boolean {
        arraySMSModel.forEach {
            if (it.address == address)
                return true
        }

        return false
    }

    private fun getNameByAddress(Address: String): String {
        var name = Address.replace("+98", "0")

        if (!isNumber(Address))
            return name

        val uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(Address))

        val contactLookup = context?.contentResolver?.query(
            uri, arrayOf(
                BaseColumns._ID,
                ContactsContract.PhoneLookup.DISPLAY_NAME
            ), null, null, null
        )

        contactLookup.use { contactLookup ->
            if (contactLookup != null && contactLookup.count > 0) {
                contactLookup.moveToNext()
                name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME))
            }
        }

        return name
    }

    private fun isNumber(value: String): Boolean {
        return try {
            value.toBigDecimal()
            true
        } catch (e: Exception) {
            false
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun makeName(): String {
        val df = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
        val date = df.format(Calendar.getInstance().time)

        return date.toString()
    }
}
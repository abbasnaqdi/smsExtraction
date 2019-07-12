package com.oky2abbas.sms2html.explorer

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

class ExplorerPresenter(val view: ExplorerView) : ExplorerContract.Presenter {
    private var context: Context? = null
    private var modelLayer: ExplorerModel? = null
    private var viewLayer: WeakReference<ExplorerContract.View>? = null

    init {
        if (context == null) context = WeakReference(view).get()?.context

        context?.let {
            if (modelLayer == null) modelLayer = ExplorerModel(it)
            if (viewLayer == null) viewLayer = WeakReference(view)
        }
    }

    private fun getView(): ExplorerContract.View? {
        return viewLayer?.get()
    }

    override fun getBackups() {
        val arrayStruct = arrayListOf<ExplorerStruct>()

        Observable.fromCallable {
            modelLayer?.getBackups()?.forEach {
                arrayStruct.add(ExplorerStruct(name = it.name, path = it.path))
            }
        }.subscribeOn(Schedulers.newThread())?.observeOn(AndroidSchedulers.mainThread())?.subscribe {
            if (arrayStruct.size > 0)
                getView()?.eventFound(arrayStruct)
            else getView()?.eventNotFound()
        }
    }
}
package com.oky2abbas.sms2html.explorer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oky2abbas.sms2html.R
import com.oky2abbas.sms2html.bus.RefreshBus
import com.oky2abbas.sms2html.extention.isPermission
import com.oky2abbas.sms2html.helper.SneakerHelper
import kotlinx.android.synthetic.main.explorer_view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ExplorerView : Fragment(), ExplorerContract.View {
    private var presLayer: ExplorerPresenter? = null
    private var explorerAdapter: ExplorerAdapter? = null
    private var sneakerHelper: SneakerHelper? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.explorer_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        if (presLayer == null) presLayer = ExplorerPresenter(this)
        if (explorerAdapter == null) explorerAdapter = ExplorerAdapter()
        if (sneakerHelper == null) sneakerHelper = SneakerHelper()

        configView()
    }

    private fun configView() {
        if (context?.isPermission()!!)
            presLayer?.getBackups()
    }

    override fun eventFound(arrayStruct: ArrayList<ExplorerStruct>) {
        flp_explorer.displayedChild = 1
        explorerAdapter?.configRecycler(arrayStruct)
        recycle_explorer.adapter = explorerAdapter
        recycle_explorer.smoothScrollToPosition(arrayStruct.size)
    }

    override fun eventNotFound() {
        flp_explorer.displayedChild = 0
    }

    override fun eventMessage(msg: String) {
        sneakerHelper?.showMessage(
            this.activity!!, msg,
            SneakerHelper.SneakerModel.SUCCESSFUL
        )
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun eventRefresh(event: RefreshBus) {
        if (context?.isPermission()!!)
            presLayer?.getBackups()

        val stickyEvent = EventBus.getDefault().getStickyEvent<RefreshBus>(RefreshBus::class.java)
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent)
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}

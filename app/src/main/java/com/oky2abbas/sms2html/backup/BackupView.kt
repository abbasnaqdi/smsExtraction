package com.oky2abbas.sms2html.backup


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oky2abbas.sms2html.R
import com.oky2abbas.sms2html.extention.isPermission
import com.oky2abbas.sms2html.helper.SneakerHelper
import kotlinx.android.synthetic.main.backup_view.*

class BackupView : Fragment(), BackupContract.View {
    private var presLayer: BackupPresenter? = null
    private var sneakerHelper: SneakerHelper? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.backup_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVIew()
    }

    private fun initVIew() {
        if (presLayer == null) presLayer = BackupPresenter(this)
        if (sneakerHelper == null) sneakerHelper = SneakerHelper()

        configView()
    }

    private fun configView() {
        btn_backup.setOnClickListener {

            val backupState = if (rdo_asc.isChecked) BackupEnum.ASC else BackupEnum.DESC

            if (context?.isPermission()!!)
                presLayer?.saveBackup(backupState)
        }
    }


    override fun eventSaved() {
        sneakerHelper?.showMessage(
            this.activity!!, getString(R.string.strBackupSaved),
            SneakerHelper.SneakerModel.SUCCESSFUL
        )
    }

    override fun eventMessage(msg: String) {
        sneakerHelper?.showMessage(
            this.activity!!, msg,
            SneakerHelper.SneakerModel.SUCCESSFUL
        )
    }
}

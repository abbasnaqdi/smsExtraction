package com.oky2abbas.sms2html.explorer

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oky2abbas.sms2html.R
import kotlinx.android.synthetic.main.explorer_item.view.*
import java.io.File


class ExplorerAdapter : RecyclerView.Adapter<ExplorerAdapter.ExplorerHolder>() {

    private var arrayStruct = arrayListOf<ExplorerStruct>()

    fun configRecycler(files: ArrayList<ExplorerStruct>) {
        arrayStruct = files
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExplorerHolder {
        return ExplorerHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.explorer_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrayStruct.size
    }

    override fun onBindViewHolder(holder: ExplorerHolder, position: Int) {
        bindView(holder, arrayStruct[position])
    }

    private fun bindView(holder: ExplorerHolder, struct: ExplorerStruct) {
        holder.itemView.txt_explorer_name.text = struct.name
        holder.itemView.setOnClickListener {
            val file = File(struct.path)

            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.fromFile(file), "text/html")
            it.context.startActivity(intent)
        }
    }

    class ExplorerHolder(view: View) : RecyclerView.ViewHolder(view)
}
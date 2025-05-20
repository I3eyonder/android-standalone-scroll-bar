package com.hieupt.android.standalonescrollbar.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by HieuPT on 12/4/2020.
 */
class ItemAdapter(
    private val count: Int,
    @LayoutRes private val layoutRes: Int = R.layout.item_text
) : RecyclerView.Adapter<ItemAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        LayoutInflater.from(parent.context).run {
            Holder(inflate(layoutRes, parent, false))
        }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = count

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            val textView = itemView as? TextView
            textView?.apply {
                text = "Item at position $adapterPosition"
                setOnClickListener { }
            }
        }
    }
}
package com.hieupt.android.standalonescrollbar.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by HieuPT on 6/2/2026.
 */
class ListItemAdapter(
    private val items: List<String>,
    @param:LayoutRes private val layoutRes: Int = R.layout.item_text,
) : RecyclerView.Adapter<ListItemAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        LayoutInflater.from(parent.context).run {
            Holder(inflate(layoutRes, parent, false))
        }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String) {
            val textView = itemView as? TextView
            textView?.apply {
                text = item
                setOnClickListener { }
            }
        }
    }
}
package com.hieupt.standalonescrollbar.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hieupt.standalonescrollbar.R

/**
 * Created by HieuPT on 12/4/2020.
 */
class MenuAdapter(
    private val onItemClickListener: (selectedMenu: Menu) -> Unit
) : RecyclerView.Adapter<MenuAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        LayoutInflater.from(parent.context).run {
            Holder(inflate(R.layout.item_text, parent, false))
        }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = Menu.values().size

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            val textView = itemView as TextView
            val menu = Menu.values()[adapterPosition]
            textView.text = menu.menuName
            textView.setOnClickListener {
                onItemClickListener(menu)
            }
        }
    }

    enum class Menu(val menuName: String) {
        VERTICAL_RECYCLER_VIEW("Vertical Recycler View"),
        HORIZONTAL_RECYCLER_VIEW("Horizontal Recycler View"),
        VERTICAL_GRID_RECYCLER_VIEW("Vertical Grid Recycler View"),
        HORIZONTAL_GRID_RECYCLER_VIEW("Horizontal Grid Recycler View"),
        NESTED_SCROLL_VIEW("Nested Scroll View"),
        SCROLL_VIEW("Scroll View"),
        HORIZONTAL_SCROLL_VIEW("Horizontal Scroll View"),
        WEB_VIEW("Web View"),
    }
}
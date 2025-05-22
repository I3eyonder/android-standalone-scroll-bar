package com.hieupt.android.standalonescrollbar.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.android.standalonescrollbar.sample.databinding.ActivityDynamicVerticalRecyclerViewBinding

class DynamicVerticalRecyclerViewActivity :
    BaseBindingActivity<ActivityDynamicVerticalRecyclerViewBinding>() {

    override val viewBindingInflater: (LayoutInflater) -> ActivityDynamicVerticalRecyclerViewBinding
        get() = ActivityDynamicVerticalRecyclerViewBinding::inflate

    private lateinit var dynamicItemAdapter: DynamicItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Dynamic Vertical Recycler View"
        setupView {
            btnAdd.setOnClickListener {
                dynamicItemAdapter.addItem()
            }
            verticalRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = DynamicItemAdapter().also {
                    dynamicItemAdapter = it
                }
            }
            verticalScrollbar.apply {
                attachTo(verticalRecyclerView)
            }
            horizontalScrollbar.apply {
                attachTo(verticalRecyclerView)
            }
        }
    }

    class DynamicItemAdapter(
        @LayoutRes private val layoutRes: Int = R.layout.item_text
    ) : ListAdapter<String, DynamicItemAdapter.Holder>(object : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

    }) {

        private var count = 0

        private fun makeList(): List<String> = buildList {
            repeat(count) {
                add("Item at position $it. Click to remove last item")
            }
        }

        fun addItem() {
            count++
            submitList(makeList())
        }

        fun removeItem() {
            count--
            submitList(makeList())
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
            LayoutInflater.from(parent.context).run {
                Holder(inflate(layoutRes, parent, false)) {
                    removeItem()
                }
            }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(currentList[position])
        }

        class Holder(itemView: View, private val onItemClick: () -> Unit) :
            RecyclerView.ViewHolder(itemView) {

            fun bind(item: String) {
                val textView = itemView as? TextView
                textView?.apply {
                    text = item
                    setOnClickListener {
                        onItemClick()
                    }
                }
            }
        }
    }
}
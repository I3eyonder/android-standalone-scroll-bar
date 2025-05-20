package com.hieupt.android.standalonescrollbar.sample

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.android.standalonescrollbar.sample.databinding.ActivityHorizontalGridRecyclerViewBinding

class HorizontalGridRecyclerViewActivity :
    BaseBindingActivity<ActivityHorizontalGridRecyclerViewBinding>() {

    override val viewBindingInflater: (LayoutInflater) -> ActivityHorizontalGridRecyclerViewBinding
        get() = ActivityHorizontalGridRecyclerViewBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Horizontal Recycler View"
        setupView {
            horizontalRecyclerView.apply {
                layoutManager = GridLayoutManager(context, 3, LinearLayoutManager.HORIZONTAL, false)
                adapter = ItemAdapter(100, R.layout.item_text_grid)
            }
            horizontalScrollbar.apply {
                attachTo(horizontalRecyclerView)
            }
            verticalScrollbar.apply {
                attachTo(horizontalRecyclerView)
            }
        }
    }
}
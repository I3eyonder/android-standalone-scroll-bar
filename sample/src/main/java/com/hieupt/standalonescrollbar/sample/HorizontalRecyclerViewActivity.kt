package com.hieupt.standalonescrollbar.sample

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.android.standalonescrollbar.sample.databinding.ActivityHorizontalRecyclerViewBinding

class HorizontalRecyclerViewActivity :
    BaseBindingActivity<ActivityHorizontalRecyclerViewBinding>() {

    override val viewBindingInflater: (LayoutInflater) -> ActivityHorizontalRecyclerViewBinding
        get() = ActivityHorizontalRecyclerViewBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Horizontal Recycler View"
        setupView {
            horizontalRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = ItemAdapter(100)
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
package com.hieupt.android.standalonescrollbar.sample

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.android.standalonescrollbar.sample.databinding.ActivityVerticalGridRecyclerViewBinding

class VerticalGridRecyclerViewActivity :
    BaseBindingActivity<ActivityVerticalGridRecyclerViewBinding>() {

    override val viewBindingInflater: (LayoutInflater) -> ActivityVerticalGridRecyclerViewBinding
        get() = ActivityVerticalGridRecyclerViewBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Vertical Grid Recycler View"
        setupView {
            verticalRecyclerView.apply {
                layoutManager = GridLayoutManager(context, 3)
                adapter = ItemAdapter(100)
            }
            verticalScrollbar.apply {
                attachTo(verticalRecyclerView)
            }
            horizontalScrollbar.apply {
                attachTo(verticalRecyclerView)
            }
        }
    }
}
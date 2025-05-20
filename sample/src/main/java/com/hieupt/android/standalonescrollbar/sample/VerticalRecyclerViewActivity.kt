package com.hieupt.android.standalonescrollbar.sample

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.android.standalonescrollbar.sample.databinding.ActivityVerticalRecyclerViewBinding

class VerticalRecyclerViewActivity : BaseBindingActivity<ActivityVerticalRecyclerViewBinding>() {

    override val viewBindingInflater: (LayoutInflater) -> ActivityVerticalRecyclerViewBinding
        get() = ActivityVerticalRecyclerViewBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Vertical Recycler View"
        setupView {
            verticalRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
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
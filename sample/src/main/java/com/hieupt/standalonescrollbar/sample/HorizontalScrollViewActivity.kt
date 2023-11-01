package com.hieupt.standalonescrollbar.sample

import android.os.Bundle
import android.view.LayoutInflater
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.android.standalonescrollbar.sample.databinding.ActivityHorizontalScrollViewBinding

class HorizontalScrollViewActivity : BaseBindingActivity<ActivityHorizontalScrollViewBinding>() {

    override val viewBindingInflater: (LayoutInflater) -> ActivityHorizontalScrollViewBinding
        get() = ActivityHorizontalScrollViewBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Horizontal Scroll View"
        setupView {
            verticalScrollbar.apply {
                attachTo(horizontalScrollView)
            }
            horizontalScrollbar.apply {
                attachTo(horizontalScrollView)
            }
        }
    }
}
package com.hieupt.standalonescrollbar.sample

import android.os.Bundle
import android.view.LayoutInflater
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.android.standalonescrollbar.sample.databinding.ActivityScrollViewBinding

class ScrollViewActivity : BaseBindingActivity<ActivityScrollViewBinding>() {

    override val viewBindingInflater: (LayoutInflater) -> ActivityScrollViewBinding
        get() = ActivityScrollViewBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Scroll View"
        setupView {
            verticalScrollbar.apply {
                attachTo(scrollView)
            }
            horizontalScrollbar.apply {
                attachTo(scrollView)
            }
        }
    }
}
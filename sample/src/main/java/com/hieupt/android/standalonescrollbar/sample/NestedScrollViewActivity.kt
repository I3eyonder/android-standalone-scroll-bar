package com.hieupt.android.standalonescrollbar.sample

import android.os.Bundle
import android.view.LayoutInflater
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.android.standalonescrollbar.sample.databinding.ActivityNestedScrollViewBinding

class NestedScrollViewActivity : BaseBindingActivity<ActivityNestedScrollViewBinding>() {

    override val viewBindingInflater: (LayoutInflater) -> ActivityNestedScrollViewBinding
        get() = ActivityNestedScrollViewBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Nested Scroll View"
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
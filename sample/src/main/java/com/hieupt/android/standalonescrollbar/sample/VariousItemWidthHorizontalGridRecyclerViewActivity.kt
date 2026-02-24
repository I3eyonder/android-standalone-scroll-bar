package com.hieupt.android.standalonescrollbar.sample

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.android.standalonescrollbar.sample.databinding.ActivityVariousItemWidthHorizontalGridRecyclerViewBinding

class VariousItemWidthHorizontalGridRecyclerViewActivity :
    BaseBindingActivity<ActivityVariousItemWidthHorizontalGridRecyclerViewBinding>() {

    override val viewBindingInflater: (LayoutInflater) -> ActivityVariousItemWidthHorizontalGridRecyclerViewBinding
        get() = ActivityVariousItemWidthHorizontalGridRecyclerViewBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Various Item Width Horizontal Grid Recycler View"
        setupView {
            horizontalRecyclerView.apply {
                layoutManager = GridLayoutManager(context, 3, LinearLayoutManager.HORIZONTAL, false)
                adapter = ListItemAdapter(
                    buildList {
                        repeat(100) {
                            if (it == 20) {
                                add("Looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong length item text at position $it")
                            } else if (it % 3 == 0) {
                                add("Looooooooong length item text at position $it")
                            } else if (it % 3 == 1) {
                                add("Medium length item text at position $it")
                            } else {
                                add("Item at position $it")
                            }
                        }
                    },
                    layoutRes = R.layout.item_text_wrap_content_width
                )
            }
            verticalScrollbar.apply {
                delayBeforeAutoHide = StandaloneScrollBar.AUTO_HIDE_SCROLLBAR_DELAY_ZERO_MILLIS
                attachTo(horizontalRecyclerView)
            }
            horizontalScrollbar.apply {
                delayBeforeAutoHide = StandaloneScrollBar.AUTO_HIDE_SCROLLBAR_DELAY_INFINITY_MILLIS
                attachTo(horizontalRecyclerView)
            }
        }
    }
}
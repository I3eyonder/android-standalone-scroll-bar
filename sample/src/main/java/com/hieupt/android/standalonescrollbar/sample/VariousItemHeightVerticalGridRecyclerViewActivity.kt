package com.hieupt.android.standalonescrollbar.sample

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.android.standalonescrollbar.sample.databinding.ActivityVariousItemHeightVerticalGridRecyclerViewBinding

class VariousItemHeightVerticalGridRecyclerViewActivity :
    BaseBindingActivity<ActivityVariousItemHeightVerticalGridRecyclerViewBinding>() {

    override val viewBindingInflater: (LayoutInflater) -> ActivityVariousItemHeightVerticalGridRecyclerViewBinding
        get() = ActivityVariousItemHeightVerticalGridRecyclerViewBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Various Item Height Vertical Grid Recycler View"
        setupView {
            verticalRecyclerView.apply {
                layoutManager = GridLayoutManager(context, 3)
                adapter = ListItemAdapter(
                    buildList {
                        repeat(100) {
                            if (it == 20) {
                                add("Item at position $it\n2nd line\n3rd line\n4th line\n5th line\n6th line\n7th line\n8th line\n9th line\n10th line\n11th line\n12th line\n13th line\n14th line\n15th line\n16th line\n17th line\n18th line\n19th line\n20th line")
                            } else if (it % 3 == 0) {
                                add("Item at position $it\n2nd line\n3rd line\n4th line\n5th line\n6th line")
                            } else if (it % 3 == 1) {
                                add("Item at position $it\n2nd line")
                            } else {
                                add("Item at position $it")
                            }
                        }
                    }
                )
            }
            verticalScrollbar.apply {
                delayBeforeAutoHide = StandaloneScrollBar.AUTO_HIDE_SCROLLBAR_DELAY_INFINITY_MILLIS
                attachTo(verticalRecyclerView)
            }
            horizontalScrollbar.apply {
                delayBeforeAutoHide = StandaloneScrollBar.AUTO_HIDE_SCROLLBAR_DELAY_ZERO_MILLIS
                attachTo(verticalRecyclerView)
            }
        }
    }
}
package com.hieupt.standalonescrollbar.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.standalonescrollbar.R
import kotlinx.android.synthetic.main.activity_horizontal_grid_recycler_view.*

class HorizontalGridRecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal_recycler_view)
        title = "Horizontal Recycler View"
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
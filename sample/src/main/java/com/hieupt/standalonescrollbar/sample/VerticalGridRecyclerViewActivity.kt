package com.hieupt.standalonescrollbar.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.hieupt.standalonescrollbar.R
import kotlinx.android.synthetic.main.activity_vertical_grid_recycler_view.*

class VerticalGridRecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vertical_grid_recycler_view)
        title = "Vertical Grid Recycler View"
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
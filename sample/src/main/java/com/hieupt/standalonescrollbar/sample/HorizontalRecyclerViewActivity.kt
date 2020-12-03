package com.hieupt.standalonescrollbar.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hieupt.standalonescrollbar.R
import kotlinx.android.synthetic.main.activity_horizontal_recycler_view.*

class HorizontalRecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal_recycler_view)
        title = "Horizontal Recycler View"
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
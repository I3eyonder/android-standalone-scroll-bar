package com.hieupt.standalonescrollbar.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.standalonescrollbar.R
import kotlinx.android.synthetic.main.activity_vertical_recycler_view.*

class VerticalRecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vertical_recycler_view)
        title = "Vertical Recycler View"
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
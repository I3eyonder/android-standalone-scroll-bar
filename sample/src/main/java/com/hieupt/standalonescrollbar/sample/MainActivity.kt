package com.hieupt.standalonescrollbar.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.standalonescrollbar.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menuRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MenuAdapter {
                val clazz = when (it) {
                    MenuAdapter.Menu.VERTICAL_RECYCLER_VIEW -> VerticalRecyclerViewActivity::class.java
                    MenuAdapter.Menu.HORIZONTAL_RECYCLER_VIEW -> HorizontalRecyclerViewActivity::class.java
                    MenuAdapter.Menu.VERTICAL_GRID_RECYCLER_VIEW -> VerticalGridRecyclerViewActivity::class.java
                    MenuAdapter.Menu.HORIZONTAL_GRID_RECYCLER_VIEW -> HorizontalGridRecyclerViewActivity::class.java
                    MenuAdapter.Menu.NESTED_SCROLL_VIEW -> NestedScrollViewActivity::class.java
                    MenuAdapter.Menu.SCROLL_VIEW -> ScrollViewActivity::class.java
                    MenuAdapter.Menu.HORIZONTAL_SCROLL_VIEW -> HorizontalScrollViewActivity::class.java
                }
                startActivity(Intent(this@MainActivity, clazz))
            }
        }
        verticalScrollbar.apply {
            attachTo(menuRecyclerView)
        }
    }
}
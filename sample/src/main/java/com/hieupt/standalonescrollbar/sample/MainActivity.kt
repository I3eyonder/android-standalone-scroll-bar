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
                val intent = when (it) {
                    MenuAdapter.Menu.VERTICAL_RECYCLER_VIEW -> Intent(
                        this@MainActivity,
                        VerticalRecyclerViewActivity::class.java
                    )
                    MenuAdapter.Menu.HORIZONTAL_RECYCLER_VIEW -> Intent(
                        this@MainActivity,
                        HorizontalRecyclerViewActivity::class.java
                    )
                    MenuAdapter.Menu.VERTICAL_GRID_RECYCLER_VIEW -> Intent(
                        this@MainActivity,
                        VerticalGridRecyclerViewActivity::class.java
                    )
                    MenuAdapter.Menu.HORIZONTAL_GRID_RECYCLER_VIEW -> Intent(
                        this@MainActivity,
                        HorizontalGridRecyclerViewActivity::class.java
                    )
                }
                startActivity(intent)
            }
        }
        verticalScrollbar.apply {
            attachTo(menuRecyclerView)
        }
    }
}
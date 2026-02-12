package com.hieupt.android.standalonescrollbar.sample

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.android.standalonescrollbar.sample.databinding.ActivityMainBinding

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override val viewBindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView {
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
                        MenuAdapter.Menu.WEB_VIEW -> WebViewActivity::class.java
                        MenuAdapter.Menu.DYNAMIC_VERTICAL_RECYCLER_VIEW -> DynamicVerticalRecyclerViewActivity::class.java
                        MenuAdapter.Menu.VARIOUS_ITEM_HEIGHT_VERTICAL_RECYCLER_VIEW -> VariousItemHeightVerticalRecyclerViewActivity::class.java
                        MenuAdapter.Menu.VARIOUS_ITEM_WIDTH_HORIZONTAL_RECYCLER_VIEW -> VariousItemWidthHorizontalRecyclerViewActivity::class.java
                        MenuAdapter.Menu.VARIOUS_ITEM_HEIGHT_VERTICAL_GRID_RECYCLER_VIEW -> VariousItemHeightVerticalGridRecyclerViewActivity::class.java
                        MenuAdapter.Menu.VARIOUS_ITEM_WIDTH_HORIZONTAL_GRID_RECYCLER_VIEW -> VariousItemWidthHorizontalGridRecyclerViewActivity::class.java
                    }
                    startActivity(Intent(this@MainActivity, clazz))
                }
            }
            verticalScrollbar.apply {
                attachTo(menuRecyclerView)
            }
        }
    }
}
package com.hieupt.standalonescrollbar.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.standalonescrollbar.R
import kotlinx.android.synthetic.main.activity_nested_scroll_view.*

class NestedScrollViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scroll_view)
        title = "Nested Scroll View"
        verticalScrollbar.apply {
            attachTo(scrollView)
        }
        horizontalScrollbar.apply {
            attachTo(scrollView)
        }
    }
}
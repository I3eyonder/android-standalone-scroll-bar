package com.hieupt.standalonescrollbar.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.standalonescrollbar.R
import kotlinx.android.synthetic.main.activity_horizontal_scroll_view.*

class HorizontalScrollViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal_scroll_view)
        title = "Horizontal Scroll View"
        verticalScrollbar.apply {
            attachTo(horizontalScrollView)
        }
        horizontalScrollbar.apply {
            attachTo(horizontalScrollView)
        }
    }
}
package com.hieupt.standalonescrollbar.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.standalonescrollbar.R
import kotlinx.android.synthetic.main.activity_scroll_view.*

class ScrollViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_view)
        title = "Scroll View"
        verticalScrollbar.apply {
            attachTo(scrollView)
        }
        horizontalScrollbar.apply {
            attachTo(scrollView)
        }
    }
}
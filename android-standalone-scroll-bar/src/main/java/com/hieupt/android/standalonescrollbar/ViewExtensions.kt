package com.hieupt.android.standalonescrollbar

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hieupt.android.standalonescrollbar.viewhelper.HorizontalRecyclerViewHelper
import com.hieupt.android.standalonescrollbar.viewhelper.VerticalRecyclerViewHelper

val View.isLayoutRtl: Boolean
    get() = layoutDirection == View.LAYOUT_DIRECTION_RTL

fun View.updateLayout(
    left: Int = this.left,
    top: Int = this.top,
    right: Int = this.right,
    bottom: Int = this.bottom
) {
    layout(left, top, right, bottom)
}

fun StandaloneScrollBar.attachTo(recyclerView: RecyclerView) {
    val layoutManager = recyclerView.layoutManager
    if (layoutManager is LinearLayoutManager) {
        when (layoutManager.orientation) {
            RecyclerView.VERTICAL -> attachTo(VerticalRecyclerViewHelper(recyclerView))
            RecyclerView.HORIZONTAL -> attachTo(HorizontalRecyclerViewHelper(recyclerView))
        }
    } else {
        throw IllegalArgumentException("LayoutManager must be instance of LinearLayoutManager and have to be set before attach with StandaloneScrollBar")
    }
}
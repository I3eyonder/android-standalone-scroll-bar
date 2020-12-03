package com.hieupt.android.standalonescrollbar

import android.view.View

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
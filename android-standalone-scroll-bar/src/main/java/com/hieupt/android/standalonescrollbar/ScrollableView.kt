package com.hieupt.android.standalonescrollbar

interface ScrollableView {
    val viewWidth: Int
    val viewHeight: Int
    val scrollRange: Long
    val scrollOffset: Long
    val scrollOffsetRange: Long
    fun addOnScrollChangedListener(onScrollChanged: (caller: ScrollableView) -> Unit)
    fun addOnDraw(onDraw: (caller: ScrollableView) -> Unit)
    fun scrollTo(offset: Int)
}
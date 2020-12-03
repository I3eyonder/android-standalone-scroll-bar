package com.hieupt.android.standalonescrollbar

interface ScrollableView {
    val viewWidth: Int
    val viewHeight: Int
    val scrollRange: Int
    val scrollOffset: Int
    val scrollOffsetRange: Int
    fun addOnScrollChangedListener(onScrollChanged: (caller: ScrollableView) -> Unit)
    fun addOnDraw(onDraw: (caller: ScrollableView) -> Unit)
    fun scrollTo(offset: Int)
}
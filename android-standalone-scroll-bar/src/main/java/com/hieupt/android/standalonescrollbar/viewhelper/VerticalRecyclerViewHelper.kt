package com.hieupt.android.standalonescrollbar.viewhelper

import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView
import com.hieupt.android.standalonescrollbar.ScrollableView
import com.hieupt.android.standalonescrollbar.VerticalScrollableView

internal class VerticalRecyclerViewHelper(
    private val view: RecyclerView,
) : VerticalScrollableView {

    override fun addOnScrollChangedListener(onScrollChanged: (caller: ScrollableView) -> Unit) {
        view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                onScrollChanged(this@VerticalRecyclerViewHelper)
            }
        })
    }

    override fun addOnDraw(onDraw: (caller: ScrollableView) -> Unit) {
        view.addItemDecoration(object : RecyclerView.ItemDecoration() {

            override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                super.onDrawOver(c, parent, state)
                onDraw(this@VerticalRecyclerViewHelper)
            }
        })

    }

    override val viewWidth: Int
        get() = view.width

    override val viewHeight: Int
        get() = view.height

    override val scrollRange: Long
        get() = view.computeVerticalScrollRange().toLong() + view.paddingTop + view.paddingBottom

    override val scrollOffset: Long
        get() = view.computeVerticalScrollOffset().toLong()

    override fun scrollTo(offset: Int) {
        view.apply {
            // Stop any scroll in progress for RecyclerView.
            stopScroll()
            val lastAdjustScrollOffset = offset - scrollOffset
            val lastScrollOffsetRange = scrollOffsetRange
            scrollBy(0, lastAdjustScrollOffset.toInt())
            // scrollOffsetRange may change after scroll, we need to adjust scroll again to make sure the scroll offset is correct.
            if (lastScrollOffsetRange != scrollOffsetRange) {
                val adjustBy = scrollOffsetRange * offset / lastScrollOffsetRange - scrollOffset
                scrollBy(0, adjustBy.toInt())
            }
        }
    }
}
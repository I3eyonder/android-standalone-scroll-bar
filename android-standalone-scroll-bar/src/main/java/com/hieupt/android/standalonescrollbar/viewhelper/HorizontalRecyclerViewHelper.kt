package com.hieupt.android.standalonescrollbar.viewhelper

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hieupt.android.standalonescrollbar.HorizontalScrollableView
import com.hieupt.android.standalonescrollbar.ScrollableView
import kotlin.math.max

internal class HorizontalRecyclerViewHelper(
    private val view: RecyclerView
) : HorizontalScrollableView {

    private val isLayoutRtl: Boolean
        get() = view.layoutDirection == View.LAYOUT_DIRECTION_RTL

    private val tempRect = Rect()

    override fun addOnScrollChangedListener(onScrollChanged: (caller: ScrollableView) -> Unit) {
        view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                onScrollChanged(this@HorizontalRecyclerViewHelper)
            }
        })
    }

    override fun addOnDraw(onDraw: (caller: ScrollableView) -> Unit) {
        view.addItemDecoration(object : RecyclerView.ItemDecoration() {

            override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                super.onDrawOver(c, parent, state)
                onDraw(this@HorizontalRecyclerViewHelper)
            }
        })
    }

    override val viewWidth: Int
        get() = view.width

    override val viewHeight: Int
        get() = view.height

    override val scrollRange: Int
        get() {
            return if (itemCount == 0 || itemWidth == 0) {
                0
            } else {
                view.paddingStart + itemCount * itemWidth + view.paddingEnd
            }
        }

    override val scrollOffset: Int
        get() {
            val firstItemPosition = firstItemPosition
            if (firstItemPosition == RecyclerView.NO_POSITION) {
                return 0
            }
            val itemWidth = itemWidth
            val firstItemStart = firstItemOffset
            return if (isLayoutRtl) {
                view.paddingStart + firstItemPosition * itemWidth + firstItemStart - view.width
            } else {
                view.paddingStart + firstItemPosition * itemWidth - firstItemStart
            }
        }

    override fun scrollTo(offset: Int) {
        // Stop any scroll in progress for RecyclerView.
        view.stopScroll()
        val scrollOffset = offset - view.paddingStart
        val itemWidth = itemWidth
        // firstItemPosition should be non-negative even if paddingTop is greater than item height.
        val firstItemPosition = max(0, scrollOffset / itemWidth)
        val firstItemStart = firstItemPosition * itemWidth - scrollOffset
        scrollToPositionWithOffset(firstItemPosition, firstItemStart)
    }

    private val itemCount: Int
        get() {
            val linearLayoutManager = horizontalLinearLayoutManager ?: return 0
            var itemCount = linearLayoutManager.itemCount
            if (itemCount == 0) {
                return 0
            }
            if (linearLayoutManager is GridLayoutManager) {
                itemCount = (itemCount - 1) / linearLayoutManager.spanCount + 1
            }
            return itemCount
        }

    private val itemWidth: Int
        get() {
            if (view.childCount == 0) {
                return 0
            }
            val itemView = view.getChildAt(0)
            view.getDecoratedBoundsWithMargins(itemView, tempRect)
            return tempRect.width()
        }

    private val firstItemPosition: Int
        get() {
            var position = firstItemAdapterPosition
            val linearLayoutManager =
                horizontalLinearLayoutManager ?: return RecyclerView.NO_POSITION
            if (linearLayoutManager is GridLayoutManager) {
                position /= linearLayoutManager.spanCount
            }
            return position
        }

    private val firstItemAdapterPosition: Int
        get() {
            if (view.childCount == 0) {
                return RecyclerView.NO_POSITION
            }
            val itemView = view.getChildAt(0)
            val linearLayoutManager =
                horizontalLinearLayoutManager ?: return RecyclerView.NO_POSITION
            return linearLayoutManager.getPosition(itemView)
        }

    private val firstItemOffset: Int
        get() {
            if (view.childCount == 0) {
                return RecyclerView.NO_POSITION
            }
            val itemView = view.getChildAt(0)
            view.getDecoratedBoundsWithMargins(itemView, tempRect)
            return if (isLayoutRtl) {
                tempRect.right
            } else {
                tempRect.left
            }
        }

    private fun scrollToPositionWithOffset(position: Int, offset: Int) {
        var scrollPosition = position
        val linearLayoutManager = horizontalLinearLayoutManager ?: return
        if (linearLayoutManager is GridLayoutManager) {
            scrollPosition *= linearLayoutManager.spanCount
        }
        // LinearLayoutManager actually takes offset from paddingStart instead of start of RecyclerView.
        val scrollOffset = offset - view.paddingStart
        linearLayoutManager.scrollToPositionWithOffset(scrollPosition, scrollOffset)
    }

    private val horizontalLinearLayoutManager: LinearLayoutManager?
        get() {
            val layoutManager = view.layoutManager as? LinearLayoutManager ?: return null
            return if (layoutManager.orientation != RecyclerView.HORIZONTAL) {
                null
            } else layoutManager
        }
}
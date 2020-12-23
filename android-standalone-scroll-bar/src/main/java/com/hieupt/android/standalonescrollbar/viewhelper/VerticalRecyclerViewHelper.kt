package com.hieupt.android.standalonescrollbar.viewhelper

import android.graphics.Canvas
import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hieupt.android.standalonescrollbar.ScrollableView
import com.hieupt.android.standalonescrollbar.VerticalScrollableView
import kotlin.math.max

internal class VerticalRecyclerViewHelper(
    private val view: RecyclerView
) : VerticalScrollableView {

    private val tempRect = Rect()

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

    override val scrollRange: Int
        get() {
            return if (itemCount == 0 || itemHeight == 0) {
                0
            } else {
                view.paddingTop + itemCount * itemHeight + view.paddingBottom
            }
        }

    override val scrollOffset: Int
        get() {
            val firstItemPosition = firstItemPosition
            if (firstItemPosition == RecyclerView.NO_POSITION) {
                return 0
            }
            val itemHeight = itemHeight
            val firstItemTop = firstItemOffset
            return view.paddingTop + firstItemPosition * itemHeight - firstItemTop
        }

    override fun scrollTo(offset: Int) {
        // Stop any scroll in progress for RecyclerView.
        view.stopScroll()
        val scrollOffset = offset - view.paddingTop
        val itemHeight = itemHeight
        // firstItemPosition should be non-negative even if paddingTop is greater than item height.
        val firstItemPosition = max(0, scrollOffset / itemHeight)
        val firstItemTop = firstItemPosition * itemHeight - scrollOffset
        scrollToPositionWithOffset(firstItemPosition, firstItemTop)
    }

    private val itemCount: Int
        get() {
            val linearLayoutManager = verticalLinearLayoutManager ?: return 0
            var itemCount = linearLayoutManager.itemCount
            if (itemCount == 0) {
                return 0
            }
            if (linearLayoutManager is GridLayoutManager) {
                itemCount = (itemCount - 1) / linearLayoutManager.spanCount + 1
            }
            return itemCount
        }

    private val itemHeight: Int
        get() {
            if (view.childCount == 0) {
                return 0
            }
            val itemView = view.getChildAt(0)
            view.getDecoratedBoundsWithMargins(itemView, tempRect)
            return tempRect.height()
        }

    private val firstItemPosition: Int
        get() {
            var position = firstItemAdapterPosition
            val linearLayoutManager = verticalLinearLayoutManager ?: return RecyclerView.NO_POSITION
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
            val linearLayoutManager = verticalLinearLayoutManager ?: return RecyclerView.NO_POSITION
            return linearLayoutManager.getPosition(itemView)
        }

    private val firstItemOffset: Int
        get() {
            if (view.childCount == 0) {
                return RecyclerView.NO_POSITION
            }
            val itemView = view.getChildAt(0)
            view.getDecoratedBoundsWithMargins(itemView, tempRect)
            return tempRect.top
        }

    private fun scrollToPositionWithOffset(position: Int, offset: Int) {
        var scrollPosition = position
        val linearLayoutManager = verticalLinearLayoutManager ?: return
        if (linearLayoutManager is GridLayoutManager) {
            scrollPosition *= linearLayoutManager.spanCount
        }
        // LinearLayoutManager actually takes offset from paddingTop instead of top of RecyclerView.
        val scrollOffset = offset - view.paddingTop
        linearLayoutManager.scrollToPositionWithOffset(scrollPosition, scrollOffset)
    }

    private val verticalLinearLayoutManager: LinearLayoutManager?
        get() {
            val layoutManager = view.layoutManager as? LinearLayoutManager ?: return null
            return if (layoutManager.orientation != RecyclerView.VERTICAL) {
                null
            } else layoutManager
        }
}
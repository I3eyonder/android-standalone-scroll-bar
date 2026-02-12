package com.hieupt.android.standalonescrollbar.viewhelper

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hieupt.android.standalonescrollbar.HorizontalScrollableView
import com.hieupt.android.standalonescrollbar.ScrollableView
import com.hieupt.android.standalonescrollbar.removeIf

internal class HorizontalRecyclerViewHelper(
    private val view: RecyclerView
) : HorizontalScrollableView {

    private val isLayoutRtl: Boolean
        get() = view.layoutDirection == View.LAYOUT_DIRECTION_RTL

    private val tempRect = Rect()
    private val cachedItemSizes = sortedMapOf<Int, Int>()
    private val cachedItemSizeValues: List<Int>
        get() = cachedItemSizes.values.take(itemCount).toList()
    private var contentSize = 0L

    override fun addOnScrollChangedListener(onScrollChanged: (caller: ScrollableView) -> Unit) {
        view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                updateContentSize()
                onScrollChanged(this@HorizontalRecyclerViewHelper)
            }
        })
    }

    override fun addOnDraw(onDraw: (caller: ScrollableView) -> Unit) {
        view.addItemDecoration(object : RecyclerView.ItemDecoration() {

            override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                super.onDrawOver(c, parent, state)
                updateContentSize()
                onDraw(this@HorizontalRecyclerViewHelper)
            }
        })
    }

    override val viewWidth: Int
        get() = view.width

    override val viewHeight: Int
        get() = view.height

    override val scrollRange: Long
        get() = view.paddingStart + contentSize + view.paddingEnd

    override val scrollOffset: Long
        get() {
            val firstItemPosition = firstItemAdapterPosition
            if (firstItemPosition == RecyclerView.NO_POSITION) {
                return 0L
            }
            // We sum the size of all elements before the first visible one
            val items = cachedItemSizeValues
            val offset = if (items.isNotEmpty()) {
                items.take(firstItemPosition).sumSize()
            } else {
                0L
            }
            return view.paddingStart + offset - firstItemOffset
        }

    override fun scrollTo(offset: Int) {
        // Stop any scroll in progress for RecyclerView.
        view.stopScroll()
        val scrollOffset = offset - view.paddingStart
        // Find the position of the element that should be at the start
        val (targetPosition, targetContentOffset) = findItemPositionAtOffset(scrollOffset)
        if (targetPosition == RecyclerView.NO_POSITION) return
        val extraOffset = targetContentOffset - scrollOffset
        scrollToPositionWithOffset(targetPosition, extraOffset)
    }

    private val itemCount: Int
        get() = layoutManager?.itemCount ?: 0

    private val firstItemAdapterPosition: Int
        get() {
            if (view.isEmpty()) {
                return RecyclerView.NO_POSITION
            }
            val itemView = view.getChildAt(0)
            val layoutManager = layoutManager ?: return RecyclerView.NO_POSITION
            return layoutManager.getPosition(itemView)
        }

    private val firstItemOffset: Int
        get() {
            if (view.isEmpty()) {
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
        val layoutManager = layoutManager ?: return
        // LinearLayoutManager actually takes offset from paddingStart instead of start of RecyclerView.
        val scrollOffset = offset - view.paddingStart
        layoutManager.scrollToPositionWithOffset(position, scrollOffset)
    }

    private val layoutManager: LinearLayoutManager?
        get() {
            val layoutManager = view.layoutManager as? LinearLayoutManager ?: return null
            return if (layoutManager.orientation != RecyclerView.HORIZONTAL) {
                null
            } else layoutManager
        }

    private fun updateContentSize() {
        val layoutManager = layoutManager ?: return
        val itemCount = this.itemCount
        cachedItemSizes.removeIf {
            it.key >= itemCount
        }
        contentSize = if (itemCount > 0) {
            for (i in 0 until itemCount) {
                val childView = layoutManager.findViewByPosition(i)
                if (childView != null) {
                    // Refreshing the cache for visible elements
                    cachedItemSizes[i] = view.getChildViewSize(childView, tempRect)
                } else if (cachedItemSizes[i] == null) {
                    // For invisible elements and no cached value yet, we use the average value
                    cachedItemSizes[i] = calculateAverageItemSize()
                }
            }
            cachedItemSizeValues.sumSize()
        } else {
            0L
        }
    }

    private fun calculateAverageItemSize(): Int {
        return cachedItemSizeValues.let { items ->
            if (items.isEmpty()) {
                // If the cache is empty, we estimate the size based on the first visible element.
                if (!view.isEmpty()) {
                    view.getChildViewSize(view.getChildAt(0), tempRect)
                } else {
                    0
                }
            } else {
                // Average size from cache
                items.average().toInt()
            }
        }
    }

    private fun Iterable<Int>.sumSize(): Long {
        val layoutManager = layoutManager
        return if (layoutManager is GridLayoutManager) {
            chunked(layoutManager.spanCount) {
                it.max().toLong()
            }.sum()
        } else {
            sumOf {
                it.toLong()
            }
        }
    }

    private fun RecyclerView.getChildViewSize(child: View, outBounds: Rect): Int {
        getDecoratedBoundsWithMargins(child, outBounds)
        return outBounds.width()
    }

    private fun findItemPositionAtOffset(offset: Int): Pair<Int, Int> {
        val layoutManager = layoutManager ?: return RecyclerView.NO_POSITION to 0
        val items = if (layoutManager is GridLayoutManager) {
            cachedItemSizeValues.chunked(layoutManager.spanCount) {
                it.max()
            }
        } else {
            cachedItemSizeValues
        }
        if (items.isEmpty()) return RecyclerView.NO_POSITION to 0
        var accumulatedSize = 0
        for (i in items.indices) {
            val itemSize = items[i]
            if (accumulatedSize + itemSize > offset) {
                val position = if (layoutManager is GridLayoutManager) {
                    i * layoutManager.spanCount
                } else {
                    i
                }
                return position to accumulatedSize
            }
            accumulatedSize += itemSize
        }
        return (items.size - 1) to accumulatedSize
    }
}
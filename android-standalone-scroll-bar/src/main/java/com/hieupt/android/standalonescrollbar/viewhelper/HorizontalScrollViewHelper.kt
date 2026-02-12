package com.hieupt.android.standalonescrollbar.viewhelper

import com.hieupt.android.standalonescrollbar.HorizontalScrollableView
import com.hieupt.android.standalonescrollbar.ScrollableView
import com.hieupt.android.standalonescrollbar.view.HorizontalScrollView2

/**
 * Created by HieuPT on 12/8/2020.
 */
internal class HorizontalScrollViewHelper(
    private val scrollView: HorizontalScrollView2
) : HorizontalScrollableView {

    override val viewWidth: Int
        get() = scrollView.width

    override val viewHeight: Int
        get() = scrollView.height

    override val scrollRange: Long
        get() = scrollView.calculateHorizontalScrollRange().toLong() + scrollView.paddingStart + scrollView.paddingEnd

    override val scrollOffset: Long
        get() = scrollView.calculateHorizontalScrollOffset().toLong()

    override fun addOnScrollChangedListener(onScrollChanged: (caller: ScrollableView) -> Unit) {
        scrollView.addOnScrollListener { _, _, _, _ ->
            onScrollChanged(this)
        }
    }

    override fun addOnDraw(onDraw: (caller: ScrollableView) -> Unit) {
        scrollView.addOnDrawListener {
            onDraw(this)
        }
    }

    override fun scrollTo(offset: Int) {
        scrollView.scrollTo(offset, scrollView.scrollY)
    }
}
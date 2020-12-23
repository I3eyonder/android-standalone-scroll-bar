package com.hieupt.android.standalonescrollbar.viewhelper

import com.hieupt.android.standalonescrollbar.ScrollableView
import com.hieupt.android.standalonescrollbar.VerticalScrollableView
import com.hieupt.android.standalonescrollbar.view.ScrollView2

/**
 * Created by HieuPT on 12/8/2020.
 */
internal class VerticalScrollViewHelper(
    private val scrollView: ScrollView2
) : VerticalScrollableView {

    override val viewWidth: Int
        get() = scrollView.width

    override val viewHeight: Int
        get() = scrollView.height

    override val scrollRange: Int
        get() = scrollView.calculateVerticalScrollRange() + scrollView.paddingTop + scrollView.paddingBottom

    override val scrollOffset: Int
        get() = scrollView.calculateVerticalScrollOffset()

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
        scrollView.scrollTo(scrollView.scrollX, offset)
    }
}
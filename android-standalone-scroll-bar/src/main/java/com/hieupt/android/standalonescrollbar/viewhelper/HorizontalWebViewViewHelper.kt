package com.hieupt.android.standalonescrollbar.viewhelper

import com.hieupt.android.standalonescrollbar.HorizontalScrollableView
import com.hieupt.android.standalonescrollbar.ScrollableView
import com.hieupt.android.standalonescrollbar.view.WebView2

/**
 * Created by HieuPT on 12/8/2020.
 */
internal class HorizontalWebViewViewHelper(
    private val webView: WebView2
) : HorizontalScrollableView {

    override val viewWidth: Int
        get() = webView.width

    override val viewHeight: Int
        get() = webView.height

    override val scrollRange: Int
        get() = webView.calculateHorizontalScrollRange()

    override val scrollOffset: Int
        get() = webView.calculateHorizontalScrollOffset()

    override fun addOnScrollChangedListener(onScrollChanged: (caller: ScrollableView) -> Unit) {
        webView.addOnScrollListener { _, _, _, _ ->
            onScrollChanged(this)
        }
    }

    override fun addOnDraw(onDraw: (caller: ScrollableView) -> Unit) {
        webView.addOnDrawListener {
            onDraw(this)
        }
    }

    override fun scrollTo(offset: Int) {
        webView.scrollTo(offset, webView.scrollY)
    }
}
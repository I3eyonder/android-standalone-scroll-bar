package com.hieupt.android.standalonescrollbar

/**
 * Created by HieuPT on 12/23/2020.
 */
interface HorizontalScrollableView : ScrollableView {

    override val scrollOffsetRange: Int
        get() = scrollRange - viewWidth
}
package com.hieupt.android.standalonescrollbar.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.ScrollView

/**
 * Created by HieuPT on 12/8/2020.
 */
class ScrollView2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    private val drawListeners = mutableSetOf<OnDrawListener>()

    private val scrollChangeListeners = mutableSetOf<OnScrollChangedListener>()

    fun addOnDrawListener(onDrawListener: OnDrawListener) {
        drawListeners.add(onDrawListener)
    }

    fun addOnScrollListener(onScrollChangeListener: OnScrollChangedListener) {
        scrollChangeListeners.add(onScrollChangeListener)
    }

    fun calculateVerticalScrollRange(): Int = computeVerticalScrollRange()

    fun calculateVerticalScrollOffset(): Int = computeVerticalScrollOffset()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawListeners.forEach { it.onDraw(canvas) }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        scrollChangeListeners.forEach {
            it.onScrollChanged(l, t, oldl, oldt)
        }
    }
}
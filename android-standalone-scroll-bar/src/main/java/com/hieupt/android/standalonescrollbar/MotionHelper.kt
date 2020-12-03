package com.hieupt.android.standalonescrollbar

import android.view.View

object MotionHelper {

    fun isInViewTouchTarget(
        parentView: View,
        view: View,
        x: Float,
        y: Float,
        minTouchTargetSize: Int
    ): Boolean {
        val scrollX: Int = parentView.scrollX
        val scrollY: Int = parentView.scrollY
        return isInTouchTarget(
            x,
            view.left - scrollX,
            view.right - scrollX,
            0,
            parentView.width,
            minTouchTargetSize
        ) && isInTouchTarget(
            y,
            view.top - scrollY,
            view.bottom - scrollY,
            0,
            parentView.height,
            minTouchTargetSize
        )
    }

    private fun isInTouchTarget(
        position: Float,
        viewStart: Int,
        viewEnd: Int,
        parentStart: Int,
        parentEnd: Int,
        minTouchTargetSize: Int
    ): Boolean {
        val viewSize = viewEnd - viewStart
        if (viewSize >= minTouchTargetSize) {
            return position >= viewStart && position < viewEnd
        }
        var touchTargetStart: Int = viewStart - (minTouchTargetSize - viewSize) / 2
        if (touchTargetStart < parentStart) {
            touchTargetStart = parentStart
        }
        var touchTargetEnd: Int = touchTargetStart + minTouchTargetSize
        if (touchTargetEnd > parentEnd) {
            touchTargetEnd = parentEnd
            touchTargetStart = touchTargetEnd - minTouchTargetSize
            if (touchTargetStart < parentStart) {
                touchTargetStart = parentStart
            }
        }
        return position >= touchTargetStart && position < touchTargetEnd
    }
}
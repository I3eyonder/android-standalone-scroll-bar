package com.hieupt.android.standalonescrollbar.visibilitymanager

import android.view.View
import android.view.animation.Interpolator
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator

class FadeVisibilityManager(
    private val showDuration: Long = SHOW_DURATION_MILLIS,
    private val hideDuration: Long = HIDE_DURATION_MILLIS
) : VisibilityManager {

    private var isShowingScrollbar = true

    override fun isTrackViewShowing(trackView: View): Boolean = trackView.alpha > 0

    override fun isThumbViewShowing(thumbView: View): Boolean = thumbView.alpha > 0

    override fun showScrollbar(trackView: View, thumbView: View, isLayoutRtl: Boolean) {
        if (!isShowingScrollbar) {
            isShowingScrollbar = true
            trackView.animate()
                .alpha(1f)
                .setDuration(showDuration)
                .setInterpolator(SHOW_SCROLLBAR_INTERPOLATOR)
                .start()
            thumbView.animate()
                .alpha(1f)
                .setDuration(showDuration)
                .setInterpolator(SHOW_SCROLLBAR_INTERPOLATOR)
                .start()
        }
    }

    override fun hideScrollbar(trackView: View, thumbView: View, isLayoutRtl: Boolean) {
        if (isShowingScrollbar) {
            isShowingScrollbar = false
            trackView.animate()
                .alpha(0f)
                .setDuration(hideDuration)
                .setInterpolator(HIDE_SCROLLBAR_INTERPOLATOR)
                .start()
            thumbView.animate()
                .alpha(0f)
                .setDuration(hideDuration)
                .setInterpolator(HIDE_SCROLLBAR_INTERPOLATOR)
                .start()
        }
    }

    override fun showScrollbarImmediately(
        trackView: View,
        thumbView: View,
        isLayoutRtl: Boolean
    ) {
        isShowingScrollbar = true
        trackView.apply {
            animate().cancel()
            alpha = 1f
        }
        thumbView.apply {
            animate().cancel()
            alpha = 1f
        }
    }

    override fun hideScrollbarImmediately(
        trackView: View,
        thumbView: View,
        isLayoutRtl: Boolean
    ) {
        isShowingScrollbar = false
        trackView.apply {
            animate().cancel()
            alpha = 0f
        }
        thumbView.apply {
            animate().cancel()
            alpha = 0f
        }
    }

    companion object {
        private const val SHOW_DURATION_MILLIS = 150L
        private const val HIDE_DURATION_MILLIS = 200L
        private val SHOW_SCROLLBAR_INTERPOLATOR: Interpolator = LinearOutSlowInInterpolator()
        private val HIDE_SCROLLBAR_INTERPOLATOR: Interpolator = FastOutLinearInInterpolator()
    }
}
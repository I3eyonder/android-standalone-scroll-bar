package com.hieupt.android.standalonescrollbar.visibilitymanager

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

object SimpleVisibilityManager : VisibilityManager {

    override fun isTrackViewShowing(trackView: View) = trackView.isVisible

    override fun isThumbViewShowing(thumbView: View) = thumbView.isVisible

    override fun showScrollbar(
        trackView: View,
        thumbView: View,
        isLayoutRtl: Boolean
    ) {
        showScrollbarImmediately(trackView, thumbView, isLayoutRtl)
    }

    override fun hideScrollbar(
        trackView: View,
        thumbView: View,
        isLayoutRtl: Boolean
    ) {
        hideScrollbarImmediately(trackView, thumbView, isLayoutRtl)
    }

    override fun showScrollbarImmediately(
        trackView: View,
        thumbView: View,
        isLayoutRtl: Boolean
    ) {
        trackView.isVisible = true
        thumbView.isVisible = true
    }

    override fun hideScrollbarImmediately(
        trackView: View,
        thumbView: View,
        isLayoutRtl: Boolean
    ) {
        trackView.isInvisible = true
        thumbView.isInvisible = true
    }
}
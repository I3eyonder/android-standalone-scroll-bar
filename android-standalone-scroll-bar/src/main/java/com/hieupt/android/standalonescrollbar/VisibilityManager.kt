package com.hieupt.android.standalonescrollbar

import android.view.View
import androidx.core.view.isVisible

/**
 * Created by HieuPT on 12/3/2020.
 */
interface VisibilityManager {

    fun isTrackViewShowing(trackView: View) = trackView.isVisible

    fun isThumbViewShowing(thumbView: View) = thumbView.isVisible

    fun showScrollbar(trackView: View, thumbView: View, isLayoutRtl: Boolean) {
        trackView.isVisible = true
        thumbView.isVisible = true
    }

    fun hideScrollbar(trackView: View, thumbView: View, isLayoutRtl: Boolean) {
        trackView.isVisible = false
        thumbView.isVisible = false
    }

}
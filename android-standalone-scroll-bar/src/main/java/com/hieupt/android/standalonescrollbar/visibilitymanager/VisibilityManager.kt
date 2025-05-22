package com.hieupt.android.standalonescrollbar.visibilitymanager

import android.view.View

/**
 * Created by HieuPT on 12/3/2020.
 */
interface VisibilityManager {
    fun isTrackViewShowing(trackView: View): Boolean
    fun isThumbViewShowing(thumbView: View): Boolean
    fun showScrollbar(trackView: View, thumbView: View, isLayoutRtl: Boolean)
    fun showScrollbarImmediately(trackView: View, thumbView: View, isLayoutRtl: Boolean)
    fun hideScrollbar(trackView: View, thumbView: View, isLayoutRtl: Boolean)
    fun hideScrollbarImmediately(trackView: View, thumbView: View, isLayoutRtl: Boolean)
}
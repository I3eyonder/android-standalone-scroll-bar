/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hieupt.android.standalonescrollbar

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

    companion object {
        private const val SHOW_DURATION_MILLIS = 150L
        private const val HIDE_DURATION_MILLIS = 200L
        private val SHOW_SCROLLBAR_INTERPOLATOR: Interpolator = LinearOutSlowInInterpolator()
        private val HIDE_SCROLLBAR_INTERPOLATOR: Interpolator = FastOutLinearInInterpolator()
    }
}
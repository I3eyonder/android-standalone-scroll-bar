package com.hieupt.android.standalonescrollbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import kotlin.math.abs

internal sealed class OrientationHelper(internal val scrollBar: StandaloneScrollBar) {

    internal val minTouchTargetSize by lazy { context.resources.getDimensionPixelSize(R.dimen.min_touch_target_size) }

    internal val touchSlop by lazy { ViewConfiguration.get(context).scaledTouchSlop }

    internal val context: Context
        get() = scrollBar.context

    init {
        updateViews()
    }

    private fun updateViews() {
        updateLayoutParams(scrollBar.trackView, scrollBar.thumbView)
        updateBackground(scrollBar.trackView, scrollBar.thumbView)
    }

    private fun updateBackground(trackView: View, thumbView: View) {
        trackView.background =
            scrollBar.customTrackDrawable ?: defaultTrackDrawable
        if (scrollBar.customTrackDrawable == null) {
            scrollBar.defaultTrackTint?.let {
                ViewCompat.setBackgroundTintList(trackView, it)
            }
        }

        thumbView.background =
            scrollBar.customThumbDrawable ?: defaultThumbDrawable
        if (scrollBar.customThumbDrawable == null) {
            scrollBar.defaultThumbTint?.let {
                ViewCompat.setBackgroundTintList(thumbView, it)
            }
        }
    }

    internal abstract val defaultTrackDrawable: Drawable?

    internal abstract val defaultThumbDrawable: Drawable?

    internal abstract fun updateLayoutParams(trackView: View, thumbView: View)

    /**
     * The range of thumb
     */
    internal abstract fun getThumbOffsetRange(): Int

    internal abstract fun updateThumbOffsetLayout()

    internal abstract fun getThumbMeasureSpec(): Pair<Int, Int>

    internal abstract fun onTouchEvent(event: MotionEvent): Boolean

    class Vertical(scrollBarView: StandaloneScrollBar) : OrientationHelper(scrollBarView) {

        private var downX = 0f
        private var downY = 0f
        private var lastY = 0f
        private var dragStartY = 0f
        private var dragStartThumbOffset = 0

        override val defaultTrackDrawable: Drawable?
            get() = ContextCompat.getDrawable(context, R.drawable.default_vertical_track)

        override val defaultThumbDrawable: Drawable?
            get() = ContextCompat.getDrawable(context, R.drawable.default_vertical_thumb)

        override fun updateLayoutParams(trackView: View, thumbView: View) {
            trackView.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            thumbView.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER_HORIZONTAL
            }
        }

        override fun getThumbOffsetRange(): Int {
            val thumbHeight = if (scrollBar.thumbView.height != 0) {
                scrollBar.thumbView.height
            } else {
                scrollBar.thumbView.background.intrinsicHeight
            }
            return scrollBar.height - scrollBar.paddingTop - scrollBar.paddingBottom - thumbHeight
        }

        override fun updateThumbOffsetLayout() {
            val thumbTop = scrollBar.getThumbOffset()
            val thumbHeight = scrollBar.thumbView.height
            scrollBar.thumbView.updateLayout(top = thumbTop, bottom = thumbTop + thumbHeight)
        }

        override fun getThumbMeasureSpec(): Pair<Int, Int> {
            val thumbWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                scrollBar.thumbView.measuredWidth,
                View.MeasureSpec.EXACTLY
            )
            val thumbHeightMeasureSpec = when {
                scrollBar.thumbLength >= 0 -> View.MeasureSpec.makeMeasureSpec(
                    scrollBar.thumbLength.coerceIn(
                        scrollBar.minThumbLength,
                        scrollBar.trackView.measuredHeight
                    ), View.MeasureSpec.EXACTLY
                )

                scrollBar.thumbLengthByTrackRatio >= 0 -> View.MeasureSpec.makeMeasureSpec(
                    (scrollBar.trackView.measuredHeight * scrollBar.thumbLengthByTrackRatio).toInt()
                        .coerceIn(
                            scrollBar.minThumbLength,
                            scrollBar.trackView.measuredHeight
                        ), View.MeasureSpec.EXACTLY
                )

                scrollBar.autoThumbLength -> View.MeasureSpec.makeMeasureSpec(
                    ((scrollBar.scrollableView.viewHeight.toFloat() / scrollBar.scrollableView.scrollRange) * scrollBar.trackView.measuredHeight).toInt()
                        .coerceIn(
                            scrollBar.minThumbLength,
                            scrollBar.trackView.measuredHeight
                        ), View.MeasureSpec.EXACTLY
                )

                else -> View.MeasureSpec.makeMeasureSpec(
                    scrollBar.thumbView.measuredHeight.coerceIn(
                        scrollBar.minThumbLength,
                        scrollBar.trackView.measuredHeight
                    ),
                    View.MeasureSpec.EXACTLY
                )
            }
            return Pair(thumbWidthMeasureSpec, thumbHeightMeasureSpec)
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            if (!scrollBar.shouldShow) {
                return false
            }
            val eventX = event.x
            val eventY = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = eventX
                    downY = eventY
                    if (scrollBar.isTrackViewShowing) {
                        dragStartY = eventY
                        if (MotionHelper.isInViewTouchTarget(
                                scrollBar,
                                scrollBar.thumbView,
                                eventX,
                                eventY,
                                minTouchTargetSize
                            )
                        ) {
                            dragStartThumbOffset = scrollBar.getThumbOffset()
                        } else {
                            dragStartThumbOffset =
                                eventY.toInt() - scrollBar.paddingTop - scrollBar.thumbView.height / 2
                            scrollBar.scrollToThumbOffset(dragStartThumbOffset)
                        }
                        scrollBar.isDragging = true
                    } else if (scrollBar.isThumbViewShowing) {
                        if (MotionHelper.isInViewTouchTarget(
                                scrollBar,
                                scrollBar.thumbView,
                                eventX,
                                eventY,
                                minTouchTargetSize
                            )
                        ) {
                            dragStartY = eventY
                            dragStartThumbOffset = scrollBar.getThumbOffset()
                            scrollBar.isDragging = true
                        }
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    if (!scrollBar.isDragging && MotionHelper.isInViewTouchTarget(
                            scrollBar,
                            scrollBar.trackView,
                            downX,
                            downY,
                            minTouchTargetSize
                        )
                        && abs(eventY - downY) > touchSlop
                    ) {
                        if (MotionHelper.isInViewTouchTarget(
                                scrollBar,
                                scrollBar.thumbView,
                                downX,
                                downY,
                                minTouchTargetSize
                            )
                        ) {
                            dragStartY = lastY
                            dragStartThumbOffset = scrollBar.getThumbOffset()
                        } else {
                            dragStartY = eventY
                            dragStartThumbOffset =
                                eventY.toInt() - scrollBar.paddingTop - scrollBar.thumbView.height / 2
                            scrollBar.scrollToThumbOffset(dragStartThumbOffset)
                        }
                        scrollBar.isDragging = true
                    }
                    if (scrollBar.isDragging) {
                        val thumbOffset = dragStartThumbOffset + (eventY - dragStartY).toInt()
                        scrollBar.scrollToThumbOffset(thumbOffset)
                    }
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> scrollBar.isDragging = false
            }
            lastY = eventY
            return scrollBar.isDragging
        }
    }

    class Horizontal(scrollBarView: StandaloneScrollBar) : OrientationHelper(scrollBarView) {

        private var downX = 0f
        private var downY = 0f
        private var lastX = 0f
        private var dragStartX = 0f
        private var dragStartThumbOffset = 0

        override val defaultTrackDrawable: Drawable?
            get() = ContextCompat.getDrawable(context, R.drawable.default_horizontal_track)

        override val defaultThumbDrawable: Drawable?
            get() = ContextCompat.getDrawable(context, R.drawable.default_horizontal_thumb)

        override fun updateLayoutParams(trackView: View, thumbView: View) {
            trackView.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            thumbView.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER_VERTICAL
            }
        }

        override fun getThumbOffsetRange(): Int {
            val thumbWidth = if (scrollBar.thumbView.width != 0) {
                scrollBar.thumbView.width
            } else {
                scrollBar.thumbView.background.intrinsicWidth
            }
            return scrollBar.width - scrollBar.paddingStart - scrollBar.paddingEnd - thumbWidth
        }

        override fun updateThumbOffsetLayout() {
            val thumbStart = scrollBar.getThumbOffset()
            val thumbWidth = scrollBar.thumbView.width
            if (scrollBar.isLayoutRtl) {
                scrollBar.thumbView.updateLayout(
                    left = scrollBar.width - scrollBar.paddingStart - scrollBar.paddingEnd - thumbStart - thumbWidth,
                    right = scrollBar.width - scrollBar.paddingStart - scrollBar.paddingEnd - thumbStart,
                )
            } else {
                scrollBar.thumbView.updateLayout(left = thumbStart, right = thumbStart + thumbWidth)
            }
        }

        override fun getThumbMeasureSpec(): Pair<Int, Int> {
            val thumbWidthMeasureSpec = when {
                scrollBar.thumbLength >= 0 -> View.MeasureSpec.makeMeasureSpec(
                    scrollBar.thumbLength.coerceIn(
                        scrollBar.minThumbLength,
                        scrollBar.trackView.measuredWidth
                    ), View.MeasureSpec.EXACTLY
                )

                scrollBar.thumbLengthByTrackRatio >= 0 -> View.MeasureSpec.makeMeasureSpec(
                    (scrollBar.trackView.measuredWidth * scrollBar.thumbLengthByTrackRatio).toInt()
                        .coerceIn(
                            scrollBar.minThumbLength,
                            scrollBar.trackView.measuredWidth
                        ), View.MeasureSpec.EXACTLY
                )

                scrollBar.autoThumbLength -> View.MeasureSpec.makeMeasureSpec(
                    ((scrollBar.scrollableView.viewWidth.toFloat() / scrollBar.scrollableView.scrollRange) * scrollBar.trackView.measuredWidth).toInt()
                        .coerceIn(
                            scrollBar.minThumbLength,
                            scrollBar.trackView.measuredWidth
                        ), View.MeasureSpec.EXACTLY
                )

                else -> View.MeasureSpec.makeMeasureSpec(
                    scrollBar.thumbView.measuredWidth.coerceIn(
                        scrollBar.minThumbLength,
                        scrollBar.trackView.measuredWidth
                    ),
                    View.MeasureSpec.EXACTLY
                )
            }
            val thumbHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                scrollBar.thumbView.measuredHeight,
                View.MeasureSpec.EXACTLY
            )
            return Pair(thumbWidthMeasureSpec, thumbHeightMeasureSpec)
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            if (!scrollBar.shouldShow) {
                return false
            }
            val eventX = event.x
            val eventY = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = eventX
                    downY = eventY
                    if (scrollBar.isTrackViewShowing) {
                        dragStartX = eventX
                        if (MotionHelper.isInViewTouchTarget(
                                scrollBar,
                                scrollBar.thumbView,
                                eventX,
                                eventY,
                                minTouchTargetSize
                            )
                        ) {
                            dragStartThumbOffset = scrollBar.getThumbOffset()
                        } else {
                            dragStartThumbOffset =
                                if (scrollBar.isLayoutRtl) {
                                    scrollBar.width - eventX.toInt() - scrollBar.paddingStart - scrollBar.thumbView.width / 2
                                } else {
                                    eventX.toInt() - scrollBar.paddingStart - scrollBar.thumbView.width / 2
                                }
                            scrollBar.scrollToThumbOffset(dragStartThumbOffset)
                        }
                        scrollBar.isDragging = true
                    } else if (scrollBar.isThumbViewShowing) {
                        if (MotionHelper.isInViewTouchTarget(
                                scrollBar,
                                scrollBar.thumbView,
                                eventX,
                                eventY,
                                minTouchTargetSize
                            )
                        ) {
                            dragStartX = eventX
                            dragStartThumbOffset = scrollBar.getThumbOffset()
                            scrollBar.isDragging = true
                        }
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    if (!scrollBar.isDragging && MotionHelper.isInViewTouchTarget(
                            scrollBar,
                            scrollBar.trackView,
                            downX,
                            downY,
                            minTouchTargetSize
                        )
                        && abs(eventX - downX) > touchSlop
                    ) {
                        if (MotionHelper.isInViewTouchTarget(
                                scrollBar,
                                scrollBar.thumbView,
                                downX,
                                downY,
                                minTouchTargetSize
                            )
                        ) {
                            dragStartX = lastX
                            dragStartThumbOffset = scrollBar.getThumbOffset()
                        } else {
                            dragStartX = eventX
                            dragStartThumbOffset =
                                if (scrollBar.isLayoutRtl) {
                                    scrollBar.width - eventX.toInt() - scrollBar.paddingStart - scrollBar.thumbView.width / 2
                                } else {
                                    eventX.toInt() - scrollBar.paddingStart - scrollBar.thumbView.width / 2
                                }
                            scrollBar.scrollToThumbOffset(dragStartThumbOffset)
                        }
                        scrollBar.isDragging = true
                    }
                    if (scrollBar.isDragging) {
                        val thumbOffset =
                            if (scrollBar.isLayoutRtl) {
                                dragStartThumbOffset + (dragStartX - eventX).toInt()
                            } else {
                                dragStartThumbOffset + (eventX - dragStartX).toInt()
                            }
                        scrollBar.scrollToThumbOffset(thumbOffset)
                    }
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> scrollBar.isDragging = false
            }
            lastX = eventX
            return scrollBar.isDragging
        }
    }
}
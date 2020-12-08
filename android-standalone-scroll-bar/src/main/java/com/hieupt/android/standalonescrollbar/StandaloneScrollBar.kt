package com.hieupt.android.standalonescrollbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.res.use
import androidx.core.math.MathUtils

/**
 * Created by HieuPT on 12/3/2020.
 */
class StandaloneScrollBar : FrameLayout {

    private lateinit var orientationHelper: OrientationHelper

    private val autoHideScrollbarRunnable by lazy { Runnable { autoHideScrollbar() } }

    private var thumbOffset = 0

    private var _customTrackDrawable: Drawable? = null

    private var _customThumbDrawable: Drawable? = null

    private var _orientation = Orientation.VERTICAL

    private var _thumbLength = Int.MIN_VALUE

    private var _thumbLengthRatio = Float.NaN

    lateinit var scrollableView: ScrollableView

    internal val trackView: View by lazy { FrameLayout(context) }

    internal val thumbView: View by lazy { FrameLayout(context) }

    internal var shouldShow: Boolean = false

    internal var isDragging: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                if (value) {
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                trackView.isPressed = value
                thumbView.isPressed = value

                if (value) {
                    cancelAutoHideScrollbar()
                    showScrollbar()
                } else {
                    postAutoHideScrollbar()
                }
            }
        }

    internal val isTrackViewShowing: Boolean
        get() = visibilityManager?.isTrackViewShowing(trackView)
            ?: DefaultVisibilityManager.isTrackViewShowing(trackView)

    internal val isThumbViewShowing: Boolean
        get() = visibilityManager?.isThumbViewShowing(thumbView)
            ?: DefaultVisibilityManager.isThumbViewShowing(thumbView)

    var orientation: Orientation
        get() = _orientation
        set(value) {
            if (value != _orientation) {
                _orientation = value
                orientationHelper = when (value) {
                    Orientation.VERTICAL -> OrientationHelper.Vertical(this)
                    else -> OrientationHelper.Horizontal(this)
                }
                requestLayout()
            }
            if (!::orientationHelper.isInitialized) {
                orientationHelper = when (value) {
                    Orientation.VERTICAL -> OrientationHelper.Vertical(this)
                    else -> OrientationHelper.Horizontal(this)
                }
            }
        }

    var visibilityManager: VisibilityManager? = null

    var customTrackDrawable: Drawable?
        get() = _customTrackDrawable
        set(value) {
            _customTrackDrawable = value
            trackView.background = value
        }

    var customThumbDrawable: Drawable?
        get() = _customThumbDrawable
        set(value) {
            _customThumbDrawable = value
            thumbView.background = value
        }

    var isAlwaysShown = false

    var delayBeforeAutoHide = AUTO_HIDE_SCROLLBAR_DELAY_MILLIS

    var thumbLength: Int
        get() = _thumbLength
        set(value) {
            if (value != _thumbLength) {
                _thumbLength = value
                requestLayout()
            }
        }

    var thumbLengthRatio: Float
        get() = _thumbLengthRatio
        set(value) {
            if (value != _thumbLengthRatio) {
                _thumbLengthRatio = value
                requestLayout()
            }
        }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StandaloneScrollBar,
            defStyleAttr,
            0
        ).use {
            _customTrackDrawable =
                it.getDrawable(R.styleable.StandaloneScrollBar_scrollbarTrackDrawable)
            _customThumbDrawable =
                it.getDrawable(R.styleable.StandaloneScrollBar_scrollbarThumbDrawable)
            orientation =
                when (it.getInt(R.styleable.StandaloneScrollBar_scrollbarOrientation, 0)) {
                    0 -> Orientation.VERTICAL
                    else -> Orientation.HORIZONTAL
                }
            isAlwaysShown =
                it.getBoolean(R.styleable.StandaloneScrollBar_scrollbarAlwaysShow, false)
            delayBeforeAutoHide = it.getInt(
                R.styleable.StandaloneScrollBar_scrollbarDelayBeforeAutoHideDuration,
                AUTO_HIDE_SCROLLBAR_DELAY_MILLIS.toInt()
            ).toLong()
            _thumbLength = it.getDimensionPixelSize(
                R.styleable.StandaloneScrollBar_scrollbarThumbLength,
                Int.MIN_VALUE
            )
            _thumbLengthRatio =
                it.getFloat(R.styleable.StandaloneScrollBar_scrollbarThumbLengthRatio, Float.NaN)

            visibilityManager = FadeVisibilityManager()
            addView(trackView)
            addView(thumbView)
            autoHideScrollbar()
        }
    }

    private fun postAutoHideScrollbar() {
        cancelAutoHideScrollbar()
        if (!isAlwaysShown) {
            postDelayed(
                autoHideScrollbarRunnable,
                delayBeforeAutoHide
            )
        }
    }

    private fun cancelAutoHideScrollbar() {
        removeCallbacks(autoHideScrollbarRunnable)
    }

    private fun autoHideScrollbar() {
        if (!isDragging) {
            hideScrollbar()
        }
    }

    private fun showScrollbar() {
        visibilityManager?.showScrollbar(trackView, thumbView, isLayoutRtl)
            ?: DefaultVisibilityManager.showScrollbar(trackView, thumbView, isLayoutRtl)
    }

    private fun hideScrollbar() {
        visibilityManager?.hideScrollbar(trackView, thumbView, isLayoutRtl)
            ?: DefaultVisibilityManager.hideScrollbar(trackView, thumbView, isLayoutRtl)
    }

    fun attachTo(scrollableView: ScrollableView) {
        this.scrollableView = scrollableView
        this.scrollableView.addOnScrollChangedListener(::onScrollChanged)
        this.scrollableView.addOnDraw(::onPreDraw)
        if (!isAlwaysShown) {
            autoHideScrollbar()
        }
    }

    fun scrollTo(offset: Int) = scrollableView.scrollTo(offset)

    internal fun getThumbOffset(): Int = thumbOffset

    internal fun scrollToThumbOffset(thumbOffset: Int) {
        val thumbOffsetRange: Int = orientationHelper.getThumbOffsetRange()
        val offset = MathUtils.clamp(thumbOffset, 0, thumbOffsetRange)
        val scrollOffset = scrollableView.scrollOffsetRange * offset / thumbOffsetRange
        scrollTo(scrollOffset)
    }

    private fun updateScrollbarState() {
        val scrollOffsetRange: Int = scrollableView.scrollOffsetRange
        shouldShow = scrollOffsetRange > 0
        thumbOffset = if (shouldShow) {
            orientationHelper.getThumbOffsetRange() * scrollableView.scrollOffset / scrollOffsetRange
        } else {
            0
        }
    }

    private fun onScrollChanged(caller: ScrollableView) {
        if (caller === scrollableView) {
            updateScrollbarState()
            if (shouldShow) {
                showScrollbar()
                postAutoHideScrollbar()
            }
        }
    }

    private fun onPreDraw(caller: ScrollableView) {
        if (caller === scrollableView) {
            updateScrollbarState()
            if (shouldShow) {
                orientationHelper.updateThumbOffsetLayout()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (thumbLength >= 0 || thumbLengthRatio >= 0) {
            val (thumbWidthMeasureSpec, thumbHeightMeasureSpec) = orientationHelper.getThumbMeasureSpec()
            thumbView.measure(thumbWidthMeasureSpec, thumbHeightMeasureSpec)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return orientationHelper.onTouchEvent(event)
    }

    enum class Orientation {
        VERTICAL,
        HORIZONTAL
    }

    companion object {
        private const val AUTO_HIDE_SCROLLBAR_DELAY_MILLIS = 1500L
    }
}
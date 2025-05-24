package com.hieupt.android.standalonescrollbar

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.ViewCompat
import androidx.core.view.doOnLayout
import com.hieupt.android.standalonescrollbar.visibilitymanager.FadeVisibilityManager
import com.hieupt.android.standalonescrollbar.visibilitymanager.VisibilityManager
import kotlin.math.max

/**
 * Created by HieuPT on 12/3/2020.
 */
class StandaloneScrollBar : FrameLayout {

    private lateinit var orientationHelper: OrientationHelper

    private val autoHideScrollbarRunnable by lazy { Runnable { autoHideScrollbar() } }

    private var lastScrollRange = Long.MIN_VALUE

    private var lastScrollOffsetRange = Long.MIN_VALUE

    private val isLayoutDirty: Boolean
        get() = lastScrollRange != scrollableView.scrollRange || lastScrollOffsetRange != scrollableView.scrollOffsetRange

    private var _customTrackDrawable: Drawable? = null

    private var _customThumbDrawable: Drawable? = null

    private var _defaultTrackTint: ColorStateList? = null

    private var _defaultThumbTint: ColorStateList? = null

    private var _orientation = Orientation.VERTICAL

    private var _delayBeforeAutoHide = DEFAULT_AUTO_HIDE_SCROLLBAR_DELAY_MILLIS

    private var _minThumbLength = 0

    private var _thumbLength = Int.MIN_VALUE

    private var _thumbLengthByTrackRatio = Float.NaN

    lateinit var scrollableView: ScrollableView

    internal var thumbOffset = 0
        private set

    internal val trackView: View by lazy { FrameLayout(context) }

    internal val thumbView: View by lazy { FrameLayout(context) }

    internal var shouldShow: Boolean = false
        private set

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
                    showScrollbar()
                } else {
                    postAutoHideScrollbar()
                }
            }
        }

    internal val isTrackViewShowing: Boolean
        get() = visibilityManager.isTrackViewShowing(trackView)

    internal val isThumbViewShowing: Boolean
        get() = visibilityManager.isThumbViewShowing(thumbView)

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

    var visibilityManager: VisibilityManager = FadeVisibilityManager()
        set(value) {
            if (!::scrollableView.isInitialized) {
                field = value
            } else {
                throw IllegalArgumentException("VisibilityManager must be set before StandaloneScrollBar attach to a ScrollableView")
            }
        }

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

    var defaultTrackTint: ColorStateList?
        get() = _defaultTrackTint
        set(value) {
            _defaultTrackTint = value
            if (_customTrackDrawable == null) {
                ViewCompat.setBackgroundTintList(trackView, value)
            }
        }

    var defaultThumbTint: ColorStateList?
        get() = _defaultThumbTint
        set(value) {
            _defaultThumbTint = value
            if (_customThumbDrawable == null) {
                ViewCompat.setBackgroundTintList(thumbView, value)
            }
        }

    var draggable = true

    val isAlwaysShown: Boolean
        get() = _delayBeforeAutoHide < 0L

    val isAlwaysHidden: Boolean
        get() = _delayBeforeAutoHide == 0L

    var delayBeforeAutoHide: Long
        get() = _delayBeforeAutoHide
        set(value) {
            val refinedValue = value.coerceAtLeast(-1L)
            _delayBeforeAutoHide = refinedValue
            if (refinedValue < 0L) {
                //Always show
                showScrollbar()
            } else if (refinedValue == 0L) {
                //Always hide
                hideScrollbarImmediately()
            } else {
                postAutoHideScrollbar()
            }
        }

    var minThumbLength: Int
        get() = _minThumbLength
        set(value) {
            val min = max(value, 0)
            if (min != _minThumbLength) {
                _minThumbLength = min
                requestLayout()
            }
        }

    var thumbLength: Int
        get() = _thumbLength
        set(value) {
            if (value != _thumbLength) {
                _thumbLength = value
                requestLayout()
            }
        }

    var thumbLengthByTrackRatio: Float
        get() = _thumbLengthByTrackRatio
        set(value) {
            if (value != _thumbLengthByTrackRatio) {
                _thumbLengthByTrackRatio = value
                requestLayout()
            }
        }

    var autoThumbLength: Boolean = false

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
            _defaultTrackTint =
                it.getColorStateList(R.styleable.StandaloneScrollBar_scrollbarDefaultTrackTint)
            _defaultThumbTint =
                it.getColorStateList(R.styleable.StandaloneScrollBar_scrollbarDefaultThumbTint)
            orientation =
                when (it.getInt(R.styleable.StandaloneScrollBar_scrollbarOrientation, 0)) {
                    0 -> Orientation.VERTICAL
                    else -> Orientation.HORIZONTAL
                }
            _delayBeforeAutoHide = it.getInt(
                R.styleable.StandaloneScrollBar_scrollbarDelayBeforeAutoHideDuration,
                DEFAULT_AUTO_HIDE_SCROLLBAR_DELAY_MILLIS.toInt()
            ).coerceAtLeast(-1).toLong()
            _minThumbLength =
                it.getDimensionPixelSize(R.styleable.StandaloneScrollBar_scrollbarMinThumbLength, 0)
            _thumbLength = it.getDimensionPixelSize(
                R.styleable.StandaloneScrollBar_scrollbarThumbLength,
                Int.MIN_VALUE
            )
            _thumbLengthByTrackRatio =
                it.getFloat(
                    R.styleable.StandaloneScrollBar_scrollbarThumbLengthByTrackRatio,
                    Float.NaN
                )
            autoThumbLength =
                it.getBoolean(R.styleable.StandaloneScrollBar_scrollbarAutoThumbLength, false)
            draggable = it.getBoolean(R.styleable.StandaloneScrollBar_scrollbarDraggable, true)

            addView(trackView)
            addView(thumbView)
        }
    }

    private fun postAutoHideScrollbar() {
        cancelAutoHideScrollbar()
        if (!isAlwaysShown) {
            if (isAlwaysHidden) {
                hideScrollbarImmediately()
            } else {
                postDelayed(
                    autoHideScrollbarRunnable,
                    _delayBeforeAutoHide
                )
            }
        }
    }

    private fun cancelAutoHideScrollbar() {
        removeCallbacks(autoHideScrollbarRunnable)
    }

    private fun autoHideScrollbar() {
        if (!isDragging && !isAlwaysShown) {
            hideScrollbar()
        }
    }

    private fun showScrollbar() {
        cancelAutoHideScrollbar()
        visibilityManager.showScrollbar(trackView, thumbView, isLayoutRtl)
    }

    private fun hideScrollbar() {
        visibilityManager.hideScrollbar(trackView, thumbView, isLayoutRtl)
    }

    private fun showScrollbarImmediately() {
        visibilityManager.showScrollbarImmediately(trackView, thumbView, isLayoutRtl)
    }

    private fun hideScrollbarImmediately() {
        cancelAutoHideScrollbar()
        visibilityManager.hideScrollbarImmediately(trackView, thumbView, isLayoutRtl)
    }

    fun attachTo(scrollableView: ScrollableView) {
        this.scrollableView = scrollableView.apply {
            addOnScrollChangedListener(::onScrollChanged)
            addOnDraw(::onPreDraw)
        }
    }

    fun scrollTo(offset: Int) = scrollableView.scrollTo(offset)

    fun setCustomTrackDrawableResource(@DrawableRes resId: Int) {
        customTrackDrawable = ContextCompat.getDrawable(context, resId)
    }

    fun setCustomThumbDrawableResource(@DrawableRes resId: Int) {
        customThumbDrawable = ContextCompat.getDrawable(context, resId)
    }

    internal fun scrollToThumbOffset(thumbOffset: Int) {
        val thumbOffsetRange = orientationHelper.getThumbOffsetRange()
        val offset = thumbOffset.coerceIn(0, thumbOffsetRange)
        val scrollOffset = scrollableView.scrollOffsetRange * offset / thumbOffsetRange
        scrollTo(scrollOffset.toInt())
    }

    private fun updateScrollbarState() {
        val scrollOffsetRange = scrollableView.scrollOffsetRange
        shouldShow = scrollOffsetRange > 0
        thumbOffset = if (shouldShow) {
            orientationHelper.getThumbOffsetRange() * scrollableView.scrollOffset / scrollOffsetRange
        } else {
            0
        }.toInt()
    }

    private fun onScrollChanged(caller: ScrollableView) {
        if (caller === scrollableView) {
            if (!isAlwaysHidden) {
                updateScrollbarState()
                if (shouldShow) {
                    showScrollbar()
                    postAutoHideScrollbar()
                } else {
                    hideScrollbarImmediately()
                }
            }
        }
    }

    private fun onPreDraw(caller: ScrollableView) {
        if (caller === scrollableView) {
            if (!isAlwaysHidden) {
                if (autoThumbLength && isLayoutDirty) {
                    requestLayout()
                }
                lastScrollRange = scrollableView.scrollRange
                lastScrollOffsetRange = scrollableView.scrollOffsetRange
                updateScrollbarState()
                if (shouldShow) {
                    doOnLayout { orientationHelper.updateThumbOffsetLayout() }
                } else {
                    hideScrollbarImmediately()
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (thumbLength >= 0 || thumbLengthByTrackRatio >= 0 || autoThumbLength || minThumbLength > 0) {
            val (thumbWidthMeasureSpec, thumbHeightMeasureSpec) = orientationHelper.getThumbMeasureSpec()
            thumbView.measure(thumbWidthMeasureSpec, thumbHeightMeasureSpec)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (draggable) orientationHelper.onTouchEvent(event) else false
    }

    enum class Orientation {
        VERTICAL,
        HORIZONTAL
    }

    companion object {
        const val DEFAULT_AUTO_HIDE_SCROLLBAR_DELAY_MILLIS = 1500L

        /**
         * Always show scrollbar
         */
        const val AUTO_HIDE_SCROLLBAR_DELAY_INFINITY_MILLIS = -1L

        /**
         * Always hide scrollbar
         */
        const val AUTO_HIDE_SCROLLBAR_DELAY_ZERO_MILLIS = 0L
    }
}
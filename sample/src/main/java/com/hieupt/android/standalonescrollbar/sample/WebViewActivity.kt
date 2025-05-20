package com.hieupt.android.standalonescrollbar.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebViewClient
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.android.standalonescrollbar.sample.databinding.ActivityWebViewBinding

class WebViewActivity : BaseBindingActivity<ActivityWebViewBinding>() {

    override val viewBindingInflater: (LayoutInflater) -> ActivityWebViewBinding
        get() = ActivityWebViewBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Web View"
        setupView {
            webView.apply {
                webViewClient = WebViewClient()
                settings.apply {
                    javaScriptEnabled = true
                    builtInZoomControls = true
                    displayZoomControls = false
                    setSupportZoom(true)
                }
                loadUrl("https://google.com")
            }
            verticalScrollbar.apply {
                attachTo(webView, StandaloneScrollBar.Orientation.VERTICAL)
            }
            horizontalScrollbar.apply {
                attachTo(webView, StandaloneScrollBar.Orientation.HORIZONTAL)
            }
        }
    }
}
package com.hieupt.standalonescrollbar.sample

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.hieupt.android.standalonescrollbar.StandaloneScrollBar
import com.hieupt.android.standalonescrollbar.attachTo
import com.hieupt.standalonescrollbar.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        title = "Web View"
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
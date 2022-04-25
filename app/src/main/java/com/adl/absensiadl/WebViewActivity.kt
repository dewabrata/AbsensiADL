package com.adl.absensiadl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_web_view)

        val webViewClient = WebViewClient()

        webView.webViewClient = webViewClient
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(JavascriptHandler(this@WebViewActivity),"MyJavascriptInterface")

        webView.loadUrl("http://192.168.0.114/androidjs")
    }


}
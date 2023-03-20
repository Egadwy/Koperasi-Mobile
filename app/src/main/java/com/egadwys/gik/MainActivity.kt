package com.egadwys.gik

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {
    private lateinit var webview: WebView
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var linearLayout: LinearLayout
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview = findViewById(R.id.webview)
        swipe = findViewById(R.id.swipe)
        linearLayout = findViewById(R.id.error)
        linearLayout.visibility = View.GONE
        webview.visibility = View.VISIBLE
        webview.loadUrl("http://192.168.10.3/GIK")
//        local
//        webview.loadUrl("http://192.168.137.1/GIK")
        webview.settings.javaScriptEnabled = true

        webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                webview.visibility = View.VISIBLE
                linearLayout.visibility = View.GONE
                swipe.isRefreshing = true
            }
            override fun onPageFinished(view: WebView, url: String) {
                webview.visibility = View.VISIBLE
                linearLayout.visibility = View.GONE
                swipe.isRefreshing = false
            }
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                webview.visibility = View.GONE
                linearLayout.visibility = View.VISIBLE
                Toast.makeText(this@MainActivity, "connect to GLOBALINDO wifi to use this app",Toast.LENGTH_LONG).show()
            }
        }

        swipe.setOnRefreshListener {
            webview.reload()
        }

    }

    override fun onBackPressed() {
        if (webview.canGoBack()){
            webview.goBack()
        }else{
            finish()
        }
    }
}
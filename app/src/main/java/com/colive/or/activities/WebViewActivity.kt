package com.colive.or.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.colive.or.App
import com.colive.or.data.FileCreatorFruitsClash
import com.colive.or.R
import com.google.android.material.snackbar.Snackbar

class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webView = findViewById(R.id.webView)

        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        intent.getStringExtra(
            "fruits_data")?.let {
            webView.loadUrl(it) }

//        try {
//            val url =  webView.loadUrl(intent.getStringExtra("fruits_data")!!)
//        } catch (e : Exception){
//           Log.d("WEB_VIEW", "WebView Fail")
//        }


        webView.settings.loadWithOverviewMode = true
        webView.settings.userAgentString.replace("wv", "")

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true


        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        isEnabled = false
                    }
                }
            })

        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                Snackbar.make(view, error.description, Snackbar.LENGTH_LONG).show()
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                CookieManager.getInstance().flush()
                if (FileCreatorFruitsClash.BASE.substringBefore("/") == url) {
                    startActivity(Intent(this@WebViewActivity, App::class.java))
                } else {
                    (application as App).file.writeData(url)
                }
            }
        }
        webView.webChromeClient = object : WebChromeClient() {

            @SuppressLint("SetJavaScriptEnabled")
            override fun onCreateWindow(
                webView: WebView?, isDialog: Boolean,
                isUserGesture: Boolean, resultMsg: Message
            ): Boolean {
                val newWebView = WebView(this@WebViewActivity)

                newWebView.settings.setSupportMultipleWindows(true)

                newWebView.settings.domStorageEnabled = true

                newWebView.settings.javaScriptEnabled = true

                newWebView.settings.javaScriptCanOpenWindowsAutomatically = true

                newWebView.webChromeClient = this

                val transport = resultMsg.obj as WebView.WebViewTransport
                transport.webView = newWebView
                resultMsg.sendToTarget()
                return true
            }
        }
    }



}
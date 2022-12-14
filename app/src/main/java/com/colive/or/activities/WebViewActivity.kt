package com.colive.or.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.webkit.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.colive.or.App
import com.colive.or.R
import com.google.android.material.snackbar.Snackbar

//const val BASE_LINK = "wildfruitsclash.today/"
//const val userAgent= "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.5359.61 Mobile Safari/537.36"
class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
//    val REQUEST_SELECT_FILE = 100
//    private val FILECHOOSER_RESULTCODE = 1
   private var uploadMessage: ValueCallback<Array<Uri>>? = null
    val getContent =   registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        uploadMessage?.onReceiveValue(it.toTypedArray())
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val BASE_LINK = "https://wildfruitsclash.today/"
        webView = findViewById(R.id.webView)
        //    val pref = "https://"


        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
//       webView.loadUrl("https://wildfruitsclash.today/")
        intent.getStringExtra(
            "fruits_data")?.let {
            webView.loadUrl(it)
        }

//        try {
//            val url =  webView.loadUrl(intent.getStringExtra("fruits_data")!!)
//        } catch (e : Exception){
//           Log.d("WEB_VIEW", "WebView Fail")
//        }

//       webView.settings.userAgentString.
       val userAgent =  WebView(this).settings.userAgentString.replace("wv", "")
          webView.settings.userAgentString = userAgent
//    val newUrl =  FileCreatorFruitsClash.BASE.substringBefore("/")
//
//       Log.d("newUrl", newUrl.toString() )

        webView.settings.loadWithOverviewMode = false
       // webView.settings.userAgentString.replace("wv", "")
      //  webView.settings.allowFileAccess = true

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.settings.allowContentAccess = true
        webView.settings.allowFileAccess = true



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
                //FileCreatorFruitsClash.BASE.substringBefore("/") == BASE_LINK
//                FileCreatorFruitsClash.BASE.substringBefore("/") == url
                    // BASE_LINK = "https://wildfruitsclash.today"

                if  (BASE_LINK == url) {

                    startActivity(Intent(this@WebViewActivity, CreditsScreen::class.java))
                    finish()
//                    val intent = Intent(this@WebViewActivity, CreditsScreen::class.java)
//                    startActivity(intent)



                } else {
                    // Log.d("base_url", "FALSE")
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

                newWebView.settings.loadWithOverviewMode = false

                val transport = resultMsg.obj as WebView.WebViewTransport
                transport.webView = newWebView
                resultMsg.sendToTarget()
                return true
            }

            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                //  return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)

//                if (uploadMessage != null) {
//                    uploadMessage?.onReceiveValue(null)
//                    uploadMessage = null
//                }
//                mUploadMessage = filePathCallback
////                uploadMessage = filePathCallback
////                val intent = fileChooserParams?.createIntent()
//
//                val intent = Intent(Intent.ACTION_GET_CONTENT)
//                intent.addCategory(Intent.CATEGORY_OPENABLE)
//                intent.type = "image/*"
                uploadMessage = filePathCallback
                val type = "image/*"
                try {
                    getContent.launch(type )
//                    startActivityForResult(intent, REQUEST_SELECT_FILE)
//                    startActivityForResult(Intent.createChooser(intent, "File Chooser"), FILECHOOSER_RESULTCODE)
                } catch (e: Exception) {
                    uploadMessage = null
                    Toast.makeText(
                        applicationContext,
                        "Cannot Open File Chooser",
                        Toast.LENGTH_LONG
                    ).show()
                    return false
                }
                return true
            }
        }
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    }
                }
            })
    }


}
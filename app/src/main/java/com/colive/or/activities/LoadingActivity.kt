package com.colive.or.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.colive.or.*
import com.colive.or.viewModel.Factory
import com.colive.or.viewModel.ViewModelFruitsClash
import com.facebook.applinks.AppLinkData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class   LoadingActivity : AppCompatActivity() {
    private lateinit var loading: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_activity)
        animLoad()

        val factory =  Factory( application as App, appflow(), deepFlow())
        val fruitView  = ViewModelProvider(this, factory)[ViewModelFruitsClash::class.java]
        fruitView.fData.observe(this) {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("fruits_data", it)
            startActivity(intent)
            finish()
        }
    }
    @Suppress("DEPRECATION")
    private fun animLoad() {
        loading = findViewById(R.id.progress_bar)
        Handler().postDelayed({
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
            finish()
        }, 10000000)

    }

    private   fun appflow(): Flow<Apps?> = callbackFlow {


        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                trySendBlocking(data)
            }

            override fun onConversionDataFail(error: String?) {
                trySendBlocking(null)
            }

            override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}
            override fun onAttributionFailure(p0: String?) {}

        }

        AppsFlyerLib.getInstance().init(APP_ID, conversionDataListener, this@LoadingActivity)
        AppsFlyerLib.getInstance().start(this@LoadingActivity)
        awaitClose()
    }
    private fun deepFlow(): Flow<String> = callbackFlow {
        val callback = AppLinkData.CompletionHandler {
            trySendBlocking(it?.targetUri.toString())
          // trySendBlocking("myapp://test1/test!2/test-3/test 4/test5/test6")
        }
        AppLinkData.fetchDeferredAppLinkData(this@LoadingActivity, callback)
        awaitClose()
    }
}



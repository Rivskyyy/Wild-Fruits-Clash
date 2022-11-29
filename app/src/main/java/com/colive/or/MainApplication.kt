package com.colive.or

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.ads.identifier.AdvertisingIdClient
import androidx.ads.identifier.AdvertisingIdInfo
import com.appsflyer.AFVersionDeclaration.init
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures.addCallback
import com.onesignal.OneSignal
import java.util.concurrent.Executors

const val APP_ID = "3X2zGCzGXdPf5Tekmzsnvm"
const val ONE_SIGNAL_ID = "9d6447ff-17a9-40b9-977f-2d1c904f2e54"

class MainApplication(context: Context, gadid: String) : Application() {
    init {
        OneSignal.initWithContext(context)
        OneSignal.setAppId(ONE_SIGNAL_ID)

        OneSignal.setExternalUserId(gadid)

    }
    //   AdvertisingIdClient.isAdvertisingIdProviderAvailable(this)

    fun tags(deep: String, campaign: String) {
        if (campaign == "null" && deep == "null"){
            OneSignal.sendTag("key2", "organic")
        } else if ( deep != "null"){
            OneSignal.sendTag("key2", deep.replace("myapp://", "").substringBefore("/"))
        } else if (campaign != "null"){
            OneSignal.sendTag("key2", campaign.substringBefore("_"))
        }
    }

    private fun determineAdvertisingInfo() {
        if (AdvertisingIdClient.isAdvertisingIdProviderAvailable(this)) {
            val advertisingIdInfoListenableFuture =
                AdvertisingIdClient.getAdvertisingIdInfo(applicationContext)

            addCallback(advertisingIdInfoListenableFuture,
                object : FutureCallback<AdvertisingIdInfo> {
                    override fun onSuccess(adInfo: AdvertisingIdInfo?) {
                        val gadid: String? = adInfo?.id
                        val providerPackageName: String? = adInfo?.providerPackageName
                        val isLimitTrackingEnabled: Boolean? =
                            adInfo?.isLimitAdTrackingEnabled
                    }

                    // Any exceptions thrown by getAdvertisingIdInfo()
                    // cause this method to be called.
                    override fun onFailure(t: Throwable) {
                        Log.e(
                            "MY_APP_TAG",
                            "Failed to connect to Advertising ID provider."
                        )
                        // Try to connect to the Advertising ID provider again or fall
                        // back to an ad solution that doesn't require using the
                        // Advertising ID library.
                    }
                }, Executors.newSingleThreadExecutor()
            )
        } else {
            // The Advertising ID client library is unavailable. Use a different
            // library to perform any required ad use cases.
        }
    }

}

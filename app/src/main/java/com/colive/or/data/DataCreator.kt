package com.colive.or.data

import android.content.res.Resources
import androidx.core.net.toUri
import com.colive.or.Apps
import com.colive.or.R
import java.util.*

class DataCreator {

companion object{
    fun createData(
        res: Resources,
        baseData: String,
        gadid: String,
        data: Apps?,
        deep: String,
        uid: String
    ): String {
        val pref = "https://"

        val url = baseData.toUri().buildUpon().apply {
            appendQueryParameter(
                res.getString(R.string.secure_get_parametr),
                res.getString(R.string.secure_key)
            )
            appendQueryParameter(
                res.getString(R.string.dev_tmz_key),
                TimeZone.getDefault().id
            )
            appendQueryParameter(
                res.getString(R.string.gadid_key),
                gadid
            )
            appendQueryParameter(
                res.getString(R.string.deeplink_key),
                deep
            )
            appendQueryParameter(
                res.getString(R.string.source_key),
                if (deep != "null"){
                    "deeplink"
                } else{
                    data?.get("media_source").toString()
                }
            )
            appendQueryParameter(
                res.getString(R.string.af_id_key),
               uid
            )
            appendQueryParameter(
                res.getString(R.string.adset_id_key),
                data?.get("adset_id").toString()
            )
            appendQueryParameter(
                res.getString(R.string.campaign_id_key),
                data?.get("campaign_id").toString()
            )
            appendQueryParameter(
                res.getString(R.string.app_campaign_key),
                data?.get("campaign").toString()
            )
            appendQueryParameter(res.getString(R.string.adset_key),
                data?.get("adset").toString())
            appendQueryParameter(
                res.getString(R.string.adgroup_key),
                data?.get("adgroup").toString()
            )
            appendQueryParameter(
                res.getString(R.string.orig_cost_key),
                data?.get("orig_cost").toString()
            )
            appendQueryParameter(
                res.getString(R.string.af_siteid_key),
                data?.get("af_siteid").toString()

            )

        }.toString()

        return  pref + url

    }

}



}



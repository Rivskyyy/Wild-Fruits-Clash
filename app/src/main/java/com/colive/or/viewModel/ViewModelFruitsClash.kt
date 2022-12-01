package com.colive.or.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsflyer.AppsFlyerLib
import com.colive.or.*
import com.colive.or.data.DataCreator
import com.colive.or.data.FileCreatorFruitsClash
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.colive.or.MainApplication

class ViewModelFruitsClash(
    private val applic : App,
    private val appsFlow: Flow<Apps?>,
    private val deepFlow: Flow<String>
) : ViewModel() {

    private val dataF = MutableLiveData<String>()
    val fData = dataF as LiveData<String>


    init{
        if (applic.file.exists()) {
            dataF.postValue(applic.file.readData())
        } else {

            viewModelScope.launch(Dispatchers.IO) {
                val data = appsFlow.first()
                val deep = deepFlow.first()
                val getAdd = AdvertisingIdClient.getAdvertisingIdInfo(applic).id.toString()
                val uId = AppsFlyerLib.getInstance().getAppsFlyerUID(applic)

//                  Log.d("data", data.toString())
//                  Log.d("data", deep.toString())
//                  Log.d("data", getAdd.toString())
//                   Log.d("data", uId.toString())

                MainApplication(applic, getAdd).tags(data?.get("campaign").toString(), deep)

                dataF.postValue(

                        DataCreator.createData(
                            res = applic.resources, baseData = FileCreatorFruitsClash.BASE,
                            gadid = getAdd,
                            data = if (deep == "null") data else null,
                            deep = deep, uid = if (deep != "null")  null else uId

                        )
                )
//                val uId_test  = if ( deep != "null") null else uId
//                Log.d("data_changed_test", uId_test.toString())
               // Log.d("data_uid", uId = if ( deep ! = "null") null else uId )


            }
        }
    }

}
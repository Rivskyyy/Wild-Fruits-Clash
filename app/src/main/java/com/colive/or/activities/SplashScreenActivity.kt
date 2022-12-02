package com.colive.or.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.File

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       startActivity(Intent(this, LoadingActivity::class.java))
       finish()
//        if (Settings.Global.getString(contentResolver,
//                    Settings.Global.ADB_ENABLED) != "1" &&
//            arrayOf(
//                "/system/app/Superuser.apk",
//                "/sbin/su",
//                "/system/bin/su",
//                "/system/xbin/su",
//                "/data/local/xbin/su",
//                "/data/local/bin/su",
//                "/system/sd/xbin/su",
//                "/system/bin/failsafe/su",
//                "/data/local/su",
//                "/su/bin/su"
//            ).any { File(it).exists() } && Settings.Global.getString(contentResolver,
//                Settings.Global.ADB_ENABLED) != "1" )
//        {
        if (startWork()) {
            startActivity(Intent(this, LoadingActivity::class.java))

          //  Log.d("ADB_CHECK", "OK")
            finish()
        } else {
            startActivity(Intent(this, CreditsScreen::class.java))

          //  Log.d("ADB_CHECK", "FAIL")

            finish()
        }

    }

    private fun startWork(): Boolean {

//        fun root()  : Boolean = try{
//            val ar = arrayOf(
//
//                "/sbin/su",
//                "/system/bin/su",
//                "/system/xbin/su",
//                "/data/local/xbin/su",
//                "/data/local/bin/su",
//                "/system/sd/xbin/su",
//                "/system/bin/failsafe/su",
//                "/data/local/su",
//                "/su/bin/su"
//
//            ).map { File(it).exists() }
//            ar.all {  !it }
//            } catch (e : Exception  ){
//                true
//            }
                fun adb () : Boolean =  Settings.Global.getString(
                contentResolver,
                Settings.Global.ADB_ENABLED
            ) != "1"

        return adb()
               // && root()
    }


}
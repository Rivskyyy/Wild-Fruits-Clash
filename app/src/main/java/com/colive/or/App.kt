package com.colive.or

import android.app.Application
import com.colive.or.data.FileCreatorFruitsClash

class App : Application() {
     lateinit var  file : FileCreatorFruitsClash

    override fun onCreate() {
        super.onCreate()

        // need to check
        file = FileCreatorFruitsClash("fruits_data", this )
    }
}
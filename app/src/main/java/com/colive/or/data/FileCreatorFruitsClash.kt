package com.colive.or.data

import android.content.Context
import java.io.File

class FileCreatorFruitsClash (private val name : String, private val  context: Context){

    companion object {
        const val BASE = "wildfruitsclash.today/wild.php"
    }

    fun exists(): Boolean = File(context.filesDir, name).exists()

    fun readData() = context.openFileInput(name).bufferedReader().useLines { it.first() }

    fun writeData(data: String) {
        if (!exists() && !data.contains(BASE)) {
            context.openFileOutput(name, Context.MODE_PRIVATE).use {
                it.write(data.toByteArray())

            }
        }


    }


}
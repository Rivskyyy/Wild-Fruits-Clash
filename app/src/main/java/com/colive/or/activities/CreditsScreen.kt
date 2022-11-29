package com.colive.or.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.colive.or.R

class CreditsScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits_screen)

        window.statusBarColor = getColor(R.color.statusBarInCreditsActivity)

      val spinner : Spinner  = findViewById(R.id.spinner)
        val array = resources.getStringArray(R.array.creditsAmount)

        ArrayAdapter.createFromResource(this,
            R.array.creditsAmount,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            spinner.adapter = adapter
        }
        val buttonStart : Button = findViewById(R.id.buttonStart)

        buttonStart.setOnClickListener {


        val intent = Intent(this, GameFruitsClash::class.java)


          var selectedAmounts   = spinner.selectedItem.toString()

           // Log.d("amount", selectedAmounts.toString())
           intent.putExtra(CREDITS,selectedAmounts)

            startActivity(intent)

        }
    }
}
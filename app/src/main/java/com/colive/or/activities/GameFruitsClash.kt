package com.colive.or.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.colive.or.R


const val CREDITS = "credits"

class GameFruitsClash : AppCompatActivity() {

    private lateinit var slot1: ImageView
    private lateinit var slot2: ImageView
    private lateinit var slot3: ImageView
    private lateinit var linearLayout: LinearLayout
    private lateinit var creditsAmount: TextView
    private lateinit var buttonSpin : Button
    private val allSlots =
        listOf(R.drawable.slot1, R.drawable.slot2, R.drawable.slot3, R.drawable.slot4)
    private var current = -1
    private var creditsL = " "
    private var listsOfSlots: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_fruits_clash)
        initViews()

        creditsL  =  intent.getStringExtra(CREDITS )!!

        creditsAmount.text = creditsL
        current = creditsL.toInt()

        window.statusBarColor = getColor(R.color.statusBarInCreditsActivity)
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.anim)
        linearLayout.startAnimation(animation)

         buttonSpin.setOnClickListener {
            setImage()
            linearLayout.startAnimation(animation)
            decrement()
            if ( current == 0 ){
                buttonSpin.isEnabled = false
            }
             win()
         }

    }

    private fun initViews() {
        slot1 = findViewById(R.id.imageSlot1)
        slot2 = findViewById(R.id.imageSlot2)
        slot3 = findViewById(R.id.imageSlot3)
        creditsAmount = findViewById(R.id.textViewInGameCredtis)
        linearLayout = findViewById(R.id.linearLayoutGame)
        buttonSpin = findViewById(R.id.buttonSpin)
    }

    private fun setImage() {

        listsOfSlots.clear()

        slot1.setImageResource(allSlots.shuffled()[0].also {
            listsOfSlots.add(it) })
        slot2.setImageResource(allSlots.shuffled()[1].also {
            listsOfSlots.add(it) })
        slot3.setImageResource(allSlots.shuffled()[2].also {
            listsOfSlots.add(it) })

    }

    private fun decrement() {
        current -= 20

        val forDisplaying = current.toString()
        creditsAmount.text = forDisplaying

    }

    private fun win(){

           if ( listsOfSlots.all {
                   it==listsOfSlots.first()}){
               current +=100
               val forDisplaying = current.toString()
               creditsAmount.text = forDisplaying
           }
    }

}

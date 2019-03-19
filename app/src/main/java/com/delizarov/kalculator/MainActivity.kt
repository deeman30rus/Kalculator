package com.delizarov.kalculator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.delizarov.views.com.delizarov.views.GridKeyPattern
import com.delizarov.views.com.delizarov.views.keyboard.KeyboardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val keyboard = findViewById<KeyboardView>(R.id.keyboard)
        keyboard.onKeyPressed = { key ->
            println("key is pressed $key")
        }
        keyboard.adapter = KeyboardView.Adapter(this@MainActivity, keyboard, GridKeyPattern.DefaultPattern)
    }
}

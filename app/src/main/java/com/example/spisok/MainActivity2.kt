package com.example.spisok

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val s = intent.getIntExtra(MainActivity.EXTRA_KEY,0)

        val textView = findViewById<TextView>(R.id.textView2)
        textView.text = s.toString()
    }
    }

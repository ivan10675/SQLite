package com.example.spisok

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val s = intent.getIntExtra(MainActivity.EXTRA_KEY,0)

        val textView = findViewById<TextView>(R.id.textViewName)
        val buttonBack =  findViewById<Button>(R.id.buttonBack)
        val buttonDelete =  findViewById<Button>(R.id.buttonDelete)
        textView.text = s.toString()
        buttonBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        buttonDelete.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }
    }
    }

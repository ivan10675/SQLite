package com.example.spisok

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class CreateActivity : AppCompatActivity() {
    private val dbHelper = DBHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val s = intent.getLongExtra(ViewActivity.EXTRA_KEY2,0)
        val textViewNameRed = findViewById<TextView>(R.id.textViewNameRed)
        val textViewFirstNameRed = findViewById<TextView>(R.id.textViewFirstNameRed)
        val textViewDateRed = findViewById<TextView>(R.id.textViewDateRed)
        val textViewTelephoneRed = findViewById<TextView>(R.id.textViewTelephoneRed)
        val buttonCancel =  findViewById<Button>(R.id.buttonСancel)
        val buttonSave =  findViewById<Button>(R.id.buttonSave)

        textViewNameRed.text = dbHelper.getById(s)?.name
        textViewFirstNameRed.text=dbHelper.getById(s)?.firstname
        textViewDateRed.text=dbHelper.getById(s)?.date
        textViewTelephoneRed.text=dbHelper.getById(s)?.tele

        buttonCancel.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        buttonSave.setOnClickListener{

        }
    }
    }

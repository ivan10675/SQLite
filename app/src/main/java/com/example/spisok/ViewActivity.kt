package com.example.spisok

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ViewActivity : AppCompatActivity() {

    private val dbHelper = DBHelper(this)
    companion object {
        const val EXTRA_KEY = "EXTRA"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val s = intent.getLongExtra(MainActivity.EXTRA_KEY,0)

        val textViewName = findViewById<TextView>(R.id.textViewName)
        val textViewFirstName = findViewById<TextView>(R.id.textViewFirstName)
        val textViewDate = findViewById<TextView>(R.id.textViewDate)
        val textViewTelephone = findViewById<TextView>(R.id.textViewTelephone)
        val buttonBack =  findViewById<Button>(R.id.buttonBack)
        val buttonDelete =  findViewById<Button>(R.id.buttonDelete)
        textViewName.text = dbHelper.getById(s)?.name
        textViewFirstName.text=dbHelper.getById(s)?.firstname
        textViewDate.text=dbHelper.getById(s)?.date
        textViewTelephone.text=dbHelper.getById(s)?.tele
        buttonBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        buttonDelete.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            dbHelper.getById(s)?.id?.let { it1 -> dbHelper.remove(it1) }
            startActivity(intent)
        }
    }
    }

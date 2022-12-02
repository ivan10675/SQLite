package com.example.spisok

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class CreateActivity : AppCompatActivity() {
    private val dbHelper = DBHelper(this)
    companion object {
        val REQUEST_CODE2 = 1
        const val RESULT_KEY2 = "result"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val id = intent.getLongExtra(ViewActivity.EXTRA_KEY2,0)
        val textViewNameRed = findViewById<EditText>(R.id.textViewNameRed)
        val textViewFirstNameRed = findViewById<EditText>(R.id.textViewFirstNameRed)
        val textViewDateRed = findViewById<EditText>(R.id.textViewDateRed)
        val textViewTelephoneRed = findViewById<EditText>(R.id.textViewTelephoneRed)
        val buttonCancel =  findViewById<Button>(R.id.buttonСancel)
        val buttonSave =  findViewById<Button>(R.id.buttonSave)

        val item = dbHelper.getById(id)
        textViewNameRed.setText(item?.name)
        textViewFirstNameRed.setText(item?.firstname)
        textViewDateRed.setText(item?.date)
        textViewTelephoneRed.setText(item?.tele)

        buttonCancel.setOnClickListener{

            finish()
        }
        buttonSave.setOnClickListener{
            item?.id?.let { it1 -> dbHelper.update(it1,textViewNameRed.text.toString(),textViewFirstNameRed.text.toString(),textViewDateRed.text.toString(),textViewTelephoneRed.text.toString()) }
            val returnIntent = Intent()
            returnIntent.putExtra(RESULT_KEY2, id)
            if(item?.name!="") setResult(Activity.RESULT_CANCELED, returnIntent)
            else setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
    }

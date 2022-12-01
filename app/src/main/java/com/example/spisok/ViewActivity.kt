package com.example.spisok

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.spisok.CreateActivity.Companion.REQUEST_CODE2

class ViewActivity : AppCompatActivity() {

    private val dbHelper = DBHelper(this)
    companion object {
        val REQUEST_CODE = 1
        const val EXTRA_KEY2 = "EXTRA"
        const val RESULT_KEY = "result"
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE2 && resultCode == Activity.RESULT_OK) {
            // получение данных от Activity2
            val id = data?.getLongExtra(CreateActivity.RESULT_KEY2 ,0)
            val textViewName = findViewById<TextView>(R.id.textViewName)
            val textViewFirstName = findViewById<TextView>(R.id.textViewFirstName)
            val textViewDate = findViewById<TextView>(R.id.textViewDate)
            val textViewTelephone = findViewById<TextView>(R.id.textViewTelephone)
            val item = id?.let { dbHelper.getById(it) }
            textViewName.text = item?.name
            textViewFirstName.text = item?.firstname
            textViewDate.text = item?.date
            textViewTelephone.text = item?.tele
            // в result лежит строка "тут какой-то результат (строка)"
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val buttonRedaction = findViewById<Button>(R.id.buttonRedaction)
        val id = intent.getLongExtra(MainActivity.EXTRA_KEY, 0)

        val textViewName = findViewById<TextView>(R.id.textViewName)
        val textViewFirstName = findViewById<TextView>(R.id.textViewFirstName)
        val textViewDate = findViewById<TextView>(R.id.textViewDate)
        val textViewTelephone = findViewById<TextView>(R.id.textViewTelephone)
        val buttonBack = findViewById<Button>(R.id.buttonBack)
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)

        val item = dbHelper.getById(id)
        textViewName.text = item?.name
        textViewFirstName.text = item?.firstname
        textViewDate.text = item?.date
        textViewTelephone.text = item?.tele

        buttonBack.setOnClickListener {

            val returnIntent = Intent()
            returnIntent.putExtra(RESULT_KEY,  id)
            setResult(Activity.RESULT_CANCELED, returnIntent)
            finish()

        }
        buttonDelete.setOnClickListener {
            item?.id?.let { it1 -> dbHelper.remove(it1) }
            val returnIntent = Intent()
            returnIntent.putExtra(RESULT_KEY, id)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
        buttonRedaction.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            intent.putExtra(EXTRA_KEY2, id)
            startActivityForResult(intent, REQUEST_CODE2)
        }

    }
}

package com.example.spisok

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spisok.CreateActivity.Companion.REQUEST_CODE2
import com.example.spisok.DBHelper.Companion.KEY_DATE
import com.example.spisok.DBHelper.Companion.KEY_FIRSTNAME
import com.example.spisok.DBHelper.Companion.KEY_ID
import com.example.spisok.DBHelper.Companion.KEY_NAME
import com.example.spisok.DBHelper.Companion.KEY_TELE
import com.example.spisok.ViewActivity.Companion.REQUEST_CODE

class MainActivity : AppCompatActivity() {
    var number = ""
    private val list = mutableListOf<Todo>()
    val filtration_list = mutableListOf<Todo>()
    private val dbHelper = DBHelper(this)

    companion object {
        const val EXTRA_KEY = "EXTRA"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // получение данных от Activity2
            val id = data?.getLongExtra(ViewActivity.RESULT_KEY, 0)
            val index = list.indexOfFirst { it.id == id }
            list.removeAt(index)
            adapter.notifyItemRemoved(index)
            // в result лежит строка "тут какой-то результат (строка)"
        }
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_CANCELED) {
            // получение данных от Activity2
            val id = data?.getLongExtra(ViewActivity.RESULT_KEY, 0)
            val index = list.indexOfFirst { it.id == id }
            val item = id?.let { dbHelper.getById(it) }
            if (item != null) {
                list[index] = item
            }
            adapter.notifyDataSetChanged()
            // в result лежит строка "тут какой-то результат (строка)"
        }
        if (requestCode == REQUEST_CODE2 && resultCode == Activity.RESULT_OK) {
            // получение данных от Activity2
            val id = data?.getLongExtra(CreateActivity.RESULT_KEY2, 0)
            val index = list.indexOfFirst { it.id == id }
            val item = id?.let { dbHelper.getById(it) }
            if (item != null) {
                list[index] = item
            }
            adapter.notifyDataSetChanged()
            // в result лежит строка "тут какой-то результат (строка)"
        }
    }

    private lateinit var adapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        var num = ""
        list.addAll(dbHelper.getAll())
        adapter = RecyclerAdapter(list) { id ->


            val intent = Intent(this, ViewActivity::class.java)
            intent.putExtra(EXTRA_KEY, id)
            startActivityForResult(intent, REQUEST_CODE)


        }


        val number = findViewById<EditText>(R.id.number)
        number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                num = s.toString() // новая строка!
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                filtration(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val buttonAdd = findViewById<Button>(R.id.button)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivityForResult(intent, CreateActivity.REQUEST_CODE2)
            //adapter.notifyItemInserted(list.lastIndex)
        }
        adapter.notifyDataSetChanged()

    }

    fun filtration(number: String) {
        if (number == "") {
            adapter.updateList(list)
        } else {
            val filtration_list = list.filter {
                it.name.contains(number, true)
            }
            adapter.updateList(filtration_list)
        }
    }
}





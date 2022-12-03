package com.example.spisok

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spisok.ViewActivity.Companion.REQUEST_CODE

class MainActivity : AppCompatActivity() {
    private val list = mutableListOf<Todo>()
    private val dbHelper = DBHelper(this)

    companion object {
        const val EXTRA_KEY = "EXTRA"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        list.clear()
        list.addAll(dbHelper.getAll())
        adapter.updateList(list)
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
        }
        adapter.notifyDataSetChanged()

    }

    fun filtration(number: String) {
        if (number == "") {
            adapter.updateList(list)
        } else {
            val filtration_list = list.filter {
                it.name.contains(number, true) ||
                        it.firstname.contains(number, true)
            }
            adapter.updateList(filtration_list)
        }
    }
}





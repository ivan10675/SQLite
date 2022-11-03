package com.example.spisok

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spisok.DBHelper.Companion.KEY_ID
import com.example.spisok.DBHelper.Companion.KEY_TITLE

class MainActivity : AppCompatActivity() {
    var number = ""
    //val filtrlist = mutableListOf<String>()
    var filter = R.id.radioButton
    val list = mutableListOf<Todo>()
    private val dbHelper = DBHelper(this)
    companion object {
        const val EXTRA_KEY = "EXTRA"
    }
    private lateinit var adapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        var num = ""
        list.addAll(dbHelper.getAll())
        adapter = RecyclerAdapter(list) {
            // адаптеру передали обработчик удаления элемента
            adapter.notifyItemRemoved(it)
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra(KEY_TITLE,list.get(it).title)
            intent.putExtra(KEY_ID,it)
            intent.putExtra(EXTRA_KEY, list[it].title)
            startActivity(intent)

        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val number = findViewById<EditText>(R.id.number)
        //val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        number.addTextChangedListener(object : TextWatcher {
            // после того, как текст редактировали
            override fun afterTextChanged(s: Editable) {

                num = s.toString() // новая строка!
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            // во время ввода, изменение строки уже произошло
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val buttonAdd = findViewById<Button>(R.id.button)
        buttonAdd.setOnClickListener {
            val s = number.text.toString()
            val id = dbHelper.add(num)
            list.add(Todo(id,s))
            adapter.notifyItemInserted(list.lastIndex)
           // filtration()
        }
       // radioGroup.setOnCheckedChangeListener { radioGroup, i ->
        //    filter = i
           // filtration()
            adapter.notifyDataSetChanged()
        }
    }

//    fun filtration() {
//        filtrlist.clear()
//        if (filter == R.id.radioButton) {
//            filtrlist.addAll(list)
//        } else if (filter == R.id.radioButtonPlus) {
//            filtrlist.addAll(list.filter { it > 0 })
//        } else if (filter == R.id.radioButtonMinus) {
//            filtrlist.addAll(list.filter { it < 0 })
//        }
 //   }


//}



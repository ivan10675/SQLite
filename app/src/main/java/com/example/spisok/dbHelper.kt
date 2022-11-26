package com.example.spisok

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Todo(val id: Long, val name: String, val firstname: String,val date:String,val tele: String )

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "tododb"
        const val TABLE_NAME = "todos"
        const val KEY_ID = "id"
        const val KEY_NAME = "name"
        const val KEY_FIRSTNAME = "firstname"
        const val KEY_DATE = "date"
        const val KEY_TELE = "tele"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_NAME (
                $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $KEY_NAME TEXT NOT NULL,
                $KEY_FIRSTNAME TEXT NOT NULL,
                $KEY_DATE TEXT NOT NULL,
                $KEY_TELE TEXT NOT NULL
            )"""
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getAll(): List<Todo> {
        val result = mutableListOf<Todo>()
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, null, null,
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val nameIndex: Int = cursor.getColumnIndex(KEY_NAME)
            val firstnameIndex: Int = cursor.getColumnIndex(KEY_FIRSTNAME)
            val dateIndex: Int = cursor.getColumnIndex(KEY_DATE)
            val teleIndex: Int = cursor.getColumnIndex(KEY_TELE)
            do {
                val todo = Todo(
                    cursor.getLong(idIndex),
                    cursor.getString(nameIndex),
                    cursor.getString(firstnameIndex),
                    cursor.getString(dateIndex),
                    cursor.getString(teleIndex)
                )
                result.add(todo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }

    fun add(name: String,firstname: String,date: String,tele:String): Long {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_FIRSTNAME, firstname)
        contentValues.put(KEY_DATE, date)
        contentValues.put(KEY_TELE, tele)
        val id = database.insert(TABLE_NAME, null, contentValues)
        close()
        return id
    }

    fun update(id: Long,name: String,firstname: String,date: String,tele:String) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_FIRSTNAME, firstname)
        contentValues.put(KEY_DATE, date)
        contentValues.put(KEY_TELE, tele)
        database.update(TABLE_NAME, contentValues, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }

    fun remove(id: Long) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }

    fun removeAll() {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, null, null)
        close()
    }
    fun getById(id: Long): Todo? {
        var result: Todo? = null
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, "$KEY_ID = ?", arrayOf(id.toString()),
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val nameIndex: Int = cursor.getColumnIndex(KEY_NAME)
            val firstnameIndex: Int = cursor.getColumnIndex(KEY_FIRSTNAME)
            val dateIndex: Int = cursor.getColumnIndex(KEY_DATE)
            val teleIndex: Int = cursor.getColumnIndex(KEY_TELE)
            result = Todo(
                cursor.getLong(idIndex),
                cursor.getString(nameIndex),
                cursor.getString(firstnameIndex),
                cursor.getString(dateIndex),
                cursor.getString(teleIndex),
            )
        }
        cursor.close()
        return result
    }
}
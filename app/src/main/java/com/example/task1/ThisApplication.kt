package com.example.task1

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.util.Log
import com.example.task1.room.ThisDatabase

/**
 * Created by user on 03/01/18.
 */
class ThisApplication: Application(){
    lateinit var clientDatabase: ThisDatabase

    override fun onCreate() {
        super.onCreate()

        clientDatabase = Room.databaseBuilder(applicationContext,
                ThisDatabase::class.java, "task-1").allowMainThreadQueries().build()
    }
}
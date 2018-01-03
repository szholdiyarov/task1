package com.example.task1

import android.app.Application
import android.arch.persistence.room.Room
import com.example.task1.room.ThisDatabase

/**
 * Created by user on 03/01/18.
 */
class ThisApplication : Application() {
    lateinit var clientDatabase: ThisDatabase
    private val databaseName = "task-1"

    override fun onCreate() {
        super.onCreate()
        clientDatabase = Room.databaseBuilder(applicationContext, ThisDatabase::class.java, databaseName).allowMainThreadQueries().build()
    }
}
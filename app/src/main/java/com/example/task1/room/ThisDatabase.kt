package com.example.task1.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.content.Context
import com.example.task1.models.Address
import com.example.task1.models.Client
import java.util.concurrent.Executors


/**
 * Created by user on 03/01/18.
 */
/**
 * The Room database that contains the Data table
 */
@Database(entities = [Client::class], version = 2, exportSchema = false)
abstract class ThisDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
}


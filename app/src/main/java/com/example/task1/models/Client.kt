package com.example.task1.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by user on 03/01/18.
 */
@Entity(tableName = "client")
data class Client(@ColumnInfo(name = "firstName") val firstName: String,@ColumnInfo(name = "secondName") val secondName: String,@ColumnInfo(name = "telNumber") val telNumber: String,@Embedded val address: Address){
    @PrimaryKey(autoGenerate = true)  var uid: Int? = null
}
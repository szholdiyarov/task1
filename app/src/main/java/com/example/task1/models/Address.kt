package com.example.task1.models

import android.arch.persistence.room.ColumnInfo

/**
 * Created by user on 03/01/18.
 */
data class Address(@ColumnInfo(name = "city") val city: String,@ColumnInfo(name = "street") val street: String,@ColumnInfo(name = "zipCode") val zipCode: String)
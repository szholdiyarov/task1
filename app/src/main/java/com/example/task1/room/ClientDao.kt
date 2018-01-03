package com.example.task1.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.task1.models.Client
import android.arch.persistence.room.Delete



/**
 * Created by user on 03/01/18.
 */
@Dao interface ClientDao{
    @Query("SELECT * FROM client") fun getAll() : List<Client>
    @Insert fun insert(client: Client)
    @Delete fun delete(client: Client)
    @Insert fun insertAll(clients: List<Client>)
}
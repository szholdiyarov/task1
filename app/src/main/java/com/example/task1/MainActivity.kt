package com.example.task1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionNewClientFloatButton = findViewById<FloatingActionButton>(R.id.floatingActionButtonAddNewClient)
        actionNewClientFloatButton.setOnClickListener {
            startActivity(CreateNewClientActivity.instance(this))
        }


    }

    override fun onResume() {
        super.onResume()
        Log.wtf("MainActivitty", "received " + (application as ThisApplication).clientDatabase.clientDao().getAll())


    }
}

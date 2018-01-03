package com.example.task1.views

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.task1.R
import com.example.task1.ThisApplication
import com.example.task1.models.Client
import com.example.task1.view_helpers.ClientsAdapter
import com.example.task1.view_helpers.ClientsDelegate


class MainActivity : AppCompatActivity(), ClientsDelegate {
    private lateinit var recyclerViewClients: RecyclerView
    private lateinit var textViewNothingToShow: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewClients = findViewById(R.id.recyclerViewClients)
        textViewNothingToShow = findViewById(R.id.textViewNothingToShow)
        val actionNewClientFloatButton = findViewById<FloatingActionButton>(R.id.floatingActionButtonAddNewClient)
        actionNewClientFloatButton.setOnClickListener {
            startActivity(CreateNewClientActivity.instance(this))
        }
    }

    override fun onResume() {
        super.onResume()
        loadClients()
    }

    override fun removeClient(client: Client) {
        (application as ThisApplication).clientDatabase.clientDao().delete(client)
        loadClients()
    }

    private fun loadClients() {
        val existingClients = (application as ThisApplication).clientDatabase.clientDao().getAll()
        if (existingClients.isNotEmpty()) {
            onClientsLoaded(existingClients)
        } else {
            onNothingToShow()
        }
    }

    private fun onClientsLoaded(clients: List<Client>) {
        recyclerViewClients.visibility = View.VISIBLE
        textViewNothingToShow.visibility = View.GONE
        showClients(clients)
    }

    private fun onNothingToShow() {
        recyclerViewClients.visibility = View.GONE
        textViewNothingToShow.visibility = View.VISIBLE
    }

    private fun showClients(clients: List<Client>) {
        val adapter = ClientsAdapter(this, clients)
        recyclerViewClients.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewClients.addItemDecoration(DividerItemDecoration(recyclerViewClients.context, LinearLayoutManager.VERTICAL))
        recyclerViewClients.adapter = adapter
    }


}

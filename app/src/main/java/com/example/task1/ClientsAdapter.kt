package com.example.task1

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.task1.models.Client

/**
 * Created by user on 03/01/18.
 */
class ClientsAdapter(private val context: Context, private val clients: List<Client>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_client, parent, false))

    override fun getItemCount(): Int = clients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val client = clients[position]
        holder.textViewName.text = context.resources.getString(R.string.client_item_name, client.firstName, client.secondName)
        holder.textViewPhoneNumber.text = context.resources.getString(R.string.client_item_phone, client.telNumber)
        holder.textViewAddress.text = context.resources.getString(R.string.client_item_address, client.address.street)
        holder.textViewUid.text = context.resources.getString(R.string.client_item_guid, client.uid).toSpanned()
        holder.itemView.setOnLongClickListener {
            (context as ClientsDelegate).removeClient(clients[position])
            true
        }
    }

}

fun String.toSpanned(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textViewName = itemView.findViewById<TextView>(R.id.textViewName)
    val textViewPhoneNumber = itemView.findViewById<TextView>(R.id.textViewPhoneNumber)
    val textViewAddress = itemView.findViewById<TextView>(R.id.textViewAddress)
    val textViewUid = itemView.findViewById<TextView>(R.id.textViewUid)
}

interface ClientsDelegate {
    fun removeClient(client: Client)
}
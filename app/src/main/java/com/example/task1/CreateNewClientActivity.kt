package com.example.task1

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.task1.models.Address
import com.example.task1.models.Client
import com.example.task1.room.ThisDatabase
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.coroutines.experimental.async
import java.io.File
import android.location.Geocoder
import android.support.design.widget.TextInputEditText
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_new_client.*
import java.util.*
import java.nio.file.Files.size


/**
 * Created by user on 03/01/18.
 */
class CreateNewClientActivity : AppCompatActivity() {
    private val placeAutoCompleteRequestCode = 1
    private var selectedAddress: Address? = null

    companion object {
        fun instance(packageContext: Context): Intent {
            return Intent(packageContext, CreateNewClientActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_client)
        val firstName = findViewById<TextInputEditText>(R.id.textInputEditTextFirstName)
        val secondName = findViewById<TextInputEditText>(R.id.textInputEditTextSecondName)
        val phoneNumber = findViewById<TextInputEditText>(R.id.autoCompleteTextViewPhoneNumber)

        val textViewAddress = findViewById<TextView>(R.id.textViewAddress)
        textViewAddress.setOnClickListener {
            openCityPicker()
        }

        val buttonSave = findViewById<Button>(R.id.buttonSave)
        buttonSave.setOnClickListener {
            save(firstName.text.toString(), secondName.text.toString(), phoneNumber.text.toString())
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                placeAutoCompleteRequestCode -> {
                    val place = PlaceAutocomplete.getPlace(this, data)
                    val addresses = Geocoder(this, Locale.getDefault()).getFromLocation(place.latLng.latitude, place.latLng.longitude, 1)
                    if (addresses != null && addresses.size > 0) {
                        textViewAddress?.text = place.address
                        selectedAddress = Address(addresses[0].locality, place.address.toString(), addresses[0].postalCode)
                    }
                }
            }
        }
    }

    private fun save(name: String, secondName: String, phoneNumber: String) {
        val dao = (application as ThisApplication).clientDatabase.clientDao()
        val client = Client(name, secondName, phoneNumber, selectedAddress!!)
        dao.insert(client)
        finish()
    }

    private fun openCityPicker() {
        val typeFilter = AutocompleteFilter.Builder()
                .setCountry("DE").setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build()
        val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(typeFilter)
                .build(this)
        startActivityForResult(intent, placeAutoCompleteRequestCode)
    }
}

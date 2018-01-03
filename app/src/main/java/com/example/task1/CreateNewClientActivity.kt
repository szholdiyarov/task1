package com.example.task1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.task1.models.Address
import com.example.task1.models.Client
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import kotlinx.android.synthetic.main.activity_new_client.*
import java.util.*


/**
 * Created by user on 03/01/18.
 */
class CreateNewClientActivity : AppCompatActivity() {
    private val placeAutoCompleteRequestCode = 1
    private val placePickerCountryCode = "DE" // Germany
    private var selectedAddress: Address? = null

    companion object {
        fun instance(packageContext: Context): Intent {
            return Intent(packageContext, CreateNewClientActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_client)
        val firstNameTextView = findViewById<TextInputEditText>(R.id.textInputEditTextFirstName)
        val secondNameTextView = findViewById<TextInputEditText>(R.id.textInputEditTextSecondName)
        val phoneNumberTextView = findViewById<TextInputEditText>(R.id.autoCompleteTextViewPhoneNumber)

        val textViewAddress = findViewById<TextView>(R.id.textViewAddress)
        textViewAddress.setOnClickListener {
            openCityPicker()
        }

        val buttonSave = findViewById<Button>(R.id.buttonSave)
        buttonSave.setOnClickListener {
            val firstName = firstNameTextView.text.toString()
            val secondName = secondNameTextView.text.toString()
            val phoneNumber = phoneNumberTextView.text.toString()
            if (allFieldsValidated(firstName, secondName, phoneNumber)) {
                save(firstName, secondName, phoneNumber)
            } else {
                showEmptyFieldError()
            }
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

    private fun allFieldsValidated(name: String, secondName: String, phoneNumber: String): Boolean {
        return name.isNotEmpty() && secondName.isNotEmpty() && phoneNumber.isNotEmpty() && selectedAddress != null
    }

    private fun save(name: String, secondName: String, phoneNumber: String) {
        val dao = (application as ThisApplication).clientDatabase.clientDao()
        val client = Client(name, secondName, phoneNumber, selectedAddress!!)
        dao.insert(client)
        finish()
    }

    private fun openCityPicker() {
        val typeFilter = AutocompleteFilter.Builder()
                .setCountry(placePickerCountryCode).setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build()
        val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(typeFilter)
                .build(this)
        startActivityForResult(intent, placeAutoCompleteRequestCode)
    }

    private fun showEmptyFieldError() {
        Toast.makeText(this, getString(R.string.error_field_empty), Toast.LENGTH_LONG).show()
    }
}

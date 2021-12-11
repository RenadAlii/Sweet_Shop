package com.example.cupcake.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

//private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {

    val dateOptions = getPickupOptions()
    private var _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> get() = _quantity

    private var _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> get() = _flavor

    private var _date = MutableLiveData<String>()
    val date: LiveData<String> get() = _date

    private var _image = MutableLiveData<String>()
    val image: LiveData<String> get() = _image

    private var _sweetName = MutableLiveData<String>()
    val sweetName: LiveData<String> get() =  _sweetName

    private var _price = MutableLiveData<Double>()

    // using Transformations.map to initialize the var to make it with local currency format
    val price: LiveData<Double>
        get() = _price


    private var _lastPrice = MutableLiveData<Double>()

    // using Transformations.map to initialize the var to make it with local currency format
    val lastPrice: LiveData<Double>
        get() = _lastPrice


    init {
        restOrder()
    }


    //fun to reset the order
     fun restOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _price.value = 0.0
        _date.value = dateOptions[0]
        _lastPrice.value = 0.0
    }



    fun setName(name: String){
        _sweetName.value = name
    }


    fun setPrice(pricee: Int){
        _price.value = pricee.toDouble()

    }
    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        updatePrice()

    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    //return true if the flavor is null or empty
    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()

    }

    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // Create list of dates starting with the current day and the following 3 days
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }


    private fun updatePrice() {

            Log.e("Devh", "updatePrice: ${_price.value.toString().toDouble()}", )
        var calculatedPrice = (quantity.value ?: 0) * _price.value.toString().toDouble()
        // If the user selected today for pickup, add the surcharge
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _lastPrice.value = calculatedPrice
    }


}

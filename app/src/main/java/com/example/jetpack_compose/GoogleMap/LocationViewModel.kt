package com.example.jetpack_compose.GoogleMap

import android.annotation.SuppressLint
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var locationState by mutableStateOf<LocationState>(LocationState.NoPermission)
    lateinit var placesClient: PlacesClient
    lateinit var geoCoder: Geocoder

    var currentLatLong by mutableStateOf(LatLng(0.0, 0.0))
    var text by mutableStateOf("")

    val locationAutofill = mutableStateListOf<Utils.AutocompleteResult>()
    private var job: Job? = null
    fun searchPlaces(query: String) {
        job?.cancel()
        locationAutofill.clear()
        job = viewModelScope.launch {
            val request = FindAutocompletePredictionsRequest.builder().setQuery(query).build()
            placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
                locationAutofill += response.autocompletePredictions.map {
                    Utils.AutocompleteResult(
                        it.getFullText(null).toString(), it.placeId
                    )
                }
            }.addOnFailureListener {
                Log.d("FAIL___",it.printStackTrace().toString() +"\n"+it.cause+"\n"+it.message)
                it.printStackTrace()
                println(it.cause)
                println(it.message)
            }
        }
    }
    fun getAddress(latLng: LatLng) {
        viewModelScope.launch {
            val address = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            text = address?.get(0)?.getAddressLine(0).toString()
        }
    }
    fun getCoordinates(result: Utils.AutocompleteResult) {
        val placeFields = listOf(Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(result.placeId, placeFields)
        placesClient.fetchPlace(request).addOnSuccessListener {
            if (it != null) {
                currentLatLong = it.place.latLng!!
            }
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }
    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        locationState = LocationState.LocationLoading
        fusedLocationProviderClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                locationState =
                    if (location == null && locationState !is LocationState.LocationAvailable) {
                        LocationState.Error
                    } else {
                        currentLatLong = LatLng(location.latitude, location.longitude)
                        LocationState.LocationAvailable(
                            LatLng(
                                location.latitude,
                                location.longitude
                            )
                        )
                    }
            }
    }
}
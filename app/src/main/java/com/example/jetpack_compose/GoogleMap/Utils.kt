@file:Suppress("UNUSED_EXPRESSION")

package com.example.jetpack_compose.GoogleMap

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import androidx.activity.ComponentActivity
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest

object Utils {
     fun requestLocationEnable(activity:Activity,viewModel:LocationViewModel,Onsuccess:()->Unit) {
        activity?.let {
            val locationRequest = LocationRequest.create()
            val builder = LocationSettingsRequest
                .Builder()
                .addLocationRequest(locationRequest)
            LocationServices
                .getSettingsClient(it)
                .checkLocationSettings(builder.build())
                .addOnSuccessListener {
                    if (it.locationSettingsStates?.isLocationPresent == true) {
                       Onsuccess
                    }
                }
                .addOnFailureListener {
                    if (it is ResolvableApiException) {
                        try {
                            it.startResolutionForResult(activity, 999)
                        } catch (e : IntentSender.SendIntentException) {
                            e.printStackTrace()
                        }
                    }
                }

        }
    }

     fun locationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }
    data class AutocompleteResult(
        val address: String,
        val placeId: String,
    )
}
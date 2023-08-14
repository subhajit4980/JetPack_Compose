package com.example.jetpack_compose.GoogleMap

import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import androidx.activity.ComponentActivity
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest

object Utils {

    fun requestLocationEnable(activity: ComponentActivity, viewModel: LocationViewModel) {
        activity?.let {
            val locationRequest = LocationRequest.create()
            val builder = LocationSettingsRequest
                .Builder()
                .addLocationRequest(locationRequest)
            val task = LocationServices
                .getSettingsClient(it)
                .checkLocationSettings(builder.build())
                .addOnSuccessListener {
                    if (it.locationSettingsStates?.isLocationPresent == true) {
                        viewModel.getCurrentLocation()
                    }
                }
                .addOnFailureListener {
                    if (it is ResolvableApiException) {
                        try {
                            it.startResolutionForResult(activity, 999)
                        } catch (e: IntentSender.SendIntentException) {
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

}
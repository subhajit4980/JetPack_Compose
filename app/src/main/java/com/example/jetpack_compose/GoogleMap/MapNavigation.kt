package com.example.jetpack_compose.GoogleMap

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.jetpack_compose.Component
import com.example.jetpack_compose.GoogleMap.Navigation.NavGraph
import com.example.jetpack_compose.GoogleMap.Utils.locationEnabled
import com.example.jetpack_compose.GoogleMap.Utils.requestLocationEnable
import com.example.jetpack_compose.ui.theme.Jetpack_composeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.tasks.Task
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapNavigation : ComponentActivity() {
    private val viewModel: LocationViewModel by viewModels()
    private lateinit var task: Task<LocationSettingsResponse>

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
//            var locationEnabledState by remember { mutableStateOf(false) }
            Jetpack_composeTheme(dynamicColor = true) {
                Surface {
                    val locationPermissionState = rememberMultiplePermissionsState(
                        listOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
                    val navController= rememberNavController()
                    NavGraph(navHostController = navController, locationPermissionState = locationPermissionState, activity =this )
//                    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
//                        if (locationPermissionState.allPermissionsGranted) {
//                            if (locationEnabled(context) or locationEnabledState) {
//                                viewModel.getCurrentLocation()
//                            } else {
//                                viewModel.locationState = LocationState.LocationDisabled
//                            }
//                        }
//                    }
//                    AnimatedContent(
//                        viewModel.locationState
//                    ) { state ->
//                        when (state) {
//                            is LocationState.NoPermission -> {
//                                Column(
//                                    modifier = Modifier.fillMaxSize(),
//                                    verticalArrangement = Arrangement.Center,
//                                    horizontalAlignment = Alignment.CenterHorizontally
//                                ) {
//                                    Card(elevation = CardDefaults.cardElevation(10.dp)) {
//                                        Column(
//                                            modifier = Modifier.padding(10.dp),
//                                            verticalArrangement = Arrangement.Center,
//                                            horizontalAlignment = Alignment.CenterHorizontally
//                                        ) {
//                                            Text("We need location permission to continue")
//                                            Button(onClick = { locationPermissionState.launchMultiplePermissionRequest() }) {
//                                                Text("Request permission")
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//
//                            is LocationState.LocationDisabled -> {
//                                Column(
//                                    modifier = Modifier.fillMaxSize(),
//                                    verticalArrangement = Arrangement.Center,
//                                    horizontalAlignment = Alignment.CenterHorizontally
//                                ) {
//                                    Card(elevation = CardDefaults.cardElevation(10.dp)) {
//                                        Column(
//                                            modifier = Modifier.padding(10.dp),
//                                            verticalArrangement = Arrangement.Center,
//                                            horizontalAlignment = Alignment.CenterHorizontally
//                                        ) {
//                                            Text("We need location to continue")
//                                            Spacer(modifier = Modifier.padding(10.dp))
//                                            Button(onClick = { requestLocationEnable(context) }) {
//                                                Text("Enable location")
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//
//                            is LocationState.LocationLoading -> {
//                                Column(
//                                    modifier = Modifier.fillMaxSize(),
//                                    verticalArrangement = Arrangement.Center,
//                                    horizontalAlignment = Alignment.CenterHorizontally
//                                ) {
//                                    CircularProgressIndicator(
//                                        modifier = Modifier.size(50.dp),
//                                        strokeWidth = 5.dp
//                                    )
//                                }
//                            }
//
//                            is LocationState.Error -> {
//                                Column(
//                                    modifier = Modifier.fillMaxSize(),
//                                    verticalArrangement = Arrangement.Center,
//                                    horizontalAlignment = Alignment.CenterHorizontally
//                                ) {
//                                    Card(elevation = CardDefaults.cardElevation(10.dp)) {
//                                        Column(
//                                            modifier = Modifier.fillMaxSize(),
//                                            verticalArrangement = Arrangement.Center,
//                                            horizontalAlignment = Alignment.CenterHorizontally
//                                        ) {
//                                            Text("Error fetching your location")
//                                            Button(onClick = { viewModel.getCurrentLocation() }) {
//                                                Text("Retry")
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//
//                            is LocationState.LocationAvailable -> {
//                                val cameraPositionState = rememberCameraPositionState {
//                                    position = CameraPosition.fromLatLngZoom(state.location, 15f)
//                                }
//                                val mapUiSettings by remember { mutableStateOf(MapUiSettings(
//                                    myLocationButtonEnabled = false,
//                                    zoomControlsEnabled = false
//                                )) }
//                                val mapProperties by remember {
//                                    mutableStateOf(
//                                        MapProperties(
//                                            isMyLocationEnabled = true,
//                                            mapType = MapType.SATELLITE
//                                        )
//                                    )
//                                }
//                                val customMapType = rememberUpdatedState(MapType.SATELLITE)
//                                GoogleMap(
//                                    modifier = Modifier.fillMaxSize(),
//                                    cameraPositionState = cameraPositionState,
//                                    uiSettings = mapUiSettings,
//                                    properties = mapProperties
//
//                                ) {
//                                    Marker(
//                                        state = rememberMarkerState(position = state.location)
//                                    )
//                                }
//                            }
//                        }
//                    }

                }
            }
        }
    }

    private fun requestLocationEnable(context: Context) {
        this.let { it ->
            val locationRequest = LocationRequest.create()
            val builder = LocationSettingsRequest
                .Builder()
                .addLocationRequest(locationRequest)

            task = LocationServices
                .getSettingsClient(it)
                .checkLocationSettings(builder.build())
                .addOnSuccessListener {
                    if (it.locationSettingsStates?.isLocationPresent == true) {
                        viewModel.getCurrentLocation()
                    }
                    Toast.makeText(context, "enabled", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    if (it is ResolvableApiException) {
                        try {
                            it.startResolutionForResult(this, 999)
                        } catch (e: IntentSender.SendIntentException) {
                            e.printStackTrace()
                        }
                    }
                }
        }
    }
}
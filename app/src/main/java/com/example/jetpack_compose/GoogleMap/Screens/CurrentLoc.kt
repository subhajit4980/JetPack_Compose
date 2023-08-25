package com.example.jetpack_compose.GoogleMap.Screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jetpack_compose.ColorPicker.Screens
import com.example.jetpack_compose.GoogleMap.LocationState
import com.example.jetpack_compose.GoogleMap.LocationViewModel
import com.example.jetpack_compose.GoogleMap.Navigation.Screen
import com.example.jetpack_compose.GoogleMap.Utils
import com.example.jetpack_compose.GoogleMap.Utils.requestLocationEnable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CurrentLoc(
    navHostController: NavHostController,
    viewModel: LocationViewModel = hiltViewModel(),
    locationPermissionState: MultiplePermissionsState,
    activity: Activity
) {

    viewModel.fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)
    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            if (Utils.locationEnabled(activity) ) {
                viewModel.getCurrentLocation()
            } else {
                viewModel.locationState = LocationState.LocationDisabled
            }
        }
    }
    AnimatedContent(
        viewModel.locationState
    ) { state ->
        when (state) {
            is LocationState.NoPermission -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(elevation = CardDefaults.cardElevation(10.dp)) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("We need location permission to continue")
                            Button(onClick = { locationPermissionState.launchMultiplePermissionRequest() }) {
                                Text("Request permission")
                            }
                        }
                    }
                }
            }

            is LocationState.LocationDisabled -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(elevation = CardDefaults.cardElevation(10.dp)) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("We need location to continue")
                            Spacer(modifier = Modifier.padding(10.dp))
                            Button(onClick = {
                                requestLocationEnable(activity, viewModel) {
                                    viewModel.getCurrentLocation()
                                }
                            }) {
                                Text("Enable location")
                            }
                        }
                    }
                }
            }

            is LocationState.LocationLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp),
                        strokeWidth = 5.dp
                    )
                }
            }

            is LocationState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(elevation = CardDefaults.cardElevation(10.dp)) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Error fetching your location")
                            Button(onClick = { viewModel.getCurrentLocation() }) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }

            is LocationState.LocationAvailable -> {
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(state.location, 15f)
                }
                val mapUiSettings by remember {
                    mutableStateOf(
                        MapUiSettings(
                            myLocationButtonEnabled = false,
                            zoomControlsEnabled = false
                        )
                    )
                }
                val mapProperties by remember {
                    mutableStateOf(
                        MapProperties(
                            isMyLocationEnabled = true,
                            mapType = MapType.SATELLITE
                        )
                    )
                }
                Column(modifier = Modifier.fillMaxSize()) {
                    Card(
                        shape = RoundedCornerShape(size = 12.dp),
                        onClick = {
                            navHostController.navigate(Screen.SearchLocation.route)
                        }

                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp, horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(size = 36.dp),
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "loaction",
                                tint = Color(0xFFFF9800)
                            )
                            Spacer(modifier = Modifier.width(width = 12.dp))
                            Text(
                                text = "Search Here",
                                fontSize = 16.sp
                            )
                        }
                        GoogleMap(
                            modifier = Modifier.fillMaxSize(),
                            cameraPositionState = cameraPositionState,
                            uiSettings = mapUiSettings,
                            properties = mapProperties

                        ) {
                            Marker(
                                state = rememberMarkerState(position = state.location)
                            )
                        }
                    }

                }
            }
        }
    }
}
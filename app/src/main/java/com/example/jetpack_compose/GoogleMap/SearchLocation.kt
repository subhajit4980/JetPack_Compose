package com.example.jetpack_compose.GoogleMap

import android.app.Activity
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jetpack_compose.BuildConfig
import com.example.jetpack_compose.GoogleMap.Utils.locationEnabled
import com.example.jetpack_compose.GoogleMap.Utils.requestLocationEnable
import com.example.jetpack_compose.ui.theme.Jetpack_composeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SearchLocation(
    navHostController: NavHostController,
    viewModel: LocationViewModel = hiltViewModel(),
    locationPermissionState: MultiplePermissionsState,
    activity: Activity
) {
    viewModel.fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)
    Places.initialize(activity, BuildConfig.MAP_API_KEY)
    viewModel.placesClient = Places.createClient(activity)
    viewModel.geoCoder = Geocoder(activity)

    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            if (locationEnabled(activity)) {
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
                Column {
                    Text("We need location permission to continue")
                    Button(onClick = { locationPermissionState.launchMultiplePermissionRequest() }) {
                        Text("Request permission")
                    }
                }
            }

            is LocationState.LocationDisabled -> {
                Column {
                    Text("We need location to continue")
                    Button(onClick = { requestLocationEnable(activity,viewModel){

                    } }) {
                        Text("Enable location")
                    }
                }
            }

            is LocationState.LocationLoading -> {
                Text("Loading Map")
            }

            is LocationState.Error -> {
                Column {
                    Text("Error fetching your location")
                    Button(onClick = { viewModel.getCurrentLocation() }) {
                        Text("Retry")
                    }
                }
            }

            is LocationState.LocationAvailable -> {
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(state.location, 15f)
                }

                val mapUiSettings by remember { mutableStateOf(MapUiSettings()) }
                val mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
                val scope = rememberCoroutineScope()

                LaunchedEffect(viewModel.currentLatLong) {
                    cameraPositionState.animate(CameraUpdateFactory.newLatLng(viewModel.currentLatLong))
                }

                LaunchedEffect(cameraPositionState.isMoving) {
                    if (!cameraPositionState.isMoving) {
                        viewModel.getAddress(cameraPositionState.position.target)
                    }
                }

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    GoogleMap(modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        uiSettings = mapUiSettings,
                        properties = mapProperties,
                        onMapClick = {
                            scope.launch {
                                cameraPositionState.animate(CameraUpdateFactory.newLatLng(it))
                            }
                        })
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.align(Alignment.Center).size(20.dp))

                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(8.dp)
                            .fillMaxWidth(),
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AnimatedVisibility(
                                viewModel.locationAutofill.isNotEmpty(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(viewModel.locationAutofill) {
                                        Row(modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                            .clickable {
                                                viewModel.text = it.address
                                                viewModel.locationAutofill.clear()
                                                viewModel.getCoordinates(it)
                                            }) {
                                            Text(it.address)
                                        }
                                    }
                                }
                                Spacer(Modifier.height(16.dp))
                            }
                            OutlinedTextField(
                                value = viewModel.text,
                                onValueChange = {
                                    viewModel.text = it
                                    viewModel.searchPlaces(it)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                label = {
                                    Text(
                                        text = "Search Here",
                                        fontSize = 16.sp
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector =  Icons.Default.LocationOn,
                                        contentDescription = ""
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

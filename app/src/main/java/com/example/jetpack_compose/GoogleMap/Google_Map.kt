package com.example.jetpack_compose.GoogleMap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.AsyncTask
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RadioButtonChecked
import androidx.compose.material.icons.outlined.Room
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.jetpack_compose.Component
import com.example.jetpack_compose.Component.DialogBox
import com.example.jetpack_compose.Component.TextInput
import com.example.jetpack_compose.Component.toast
import com.example.jetpack_compose.Constants.apiKey
import com.example.jetpack_compose.R
import com.example.jetpack_compose.ui.theme.Jetpack_composeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.Places
import com.google.gson.Gson
import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.model.GeocodingResult
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

@AndroidEntryPoint
class Google_Map : ComponentActivity() {
    var source = "Ragpur,pingla,721131"
    var destination = "kolkata"
    private lateinit var mMap: GoogleMap
    val lineoption = PolylineOptions()
    private val viewModel: LocationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        setContent {
            Jetpack_composeTheme(dynamicColor = true) {
                Component.darkmode(LocalContext.current)
                Surface() {
                    MapUI()

                }
            }
        }
    }

    @SuppressLint("NewApi")
    @OptIn(ExperimentalPermissionsApi::class)
    @Preview(showBackground = true)
    @Composable
    private fun MapUI() {
        val multiplePermissionState = rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(44.810058, 20.4617586), 17f)
        }
        LaunchedEffect(Unit) {
            multiplePermissionState.launchMultiplePermissionRequest()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "Welcome to the MapsApp!",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
//            Row() {
            source =
                TextInput("Your location", Icons.Outlined.RadioButtonChecked, KeyboardType.Text) {}
            Spacer(modifier = Modifier.height(10.dp))
            destination = TextInput(
                "Your Destination",
                Icons.Outlined.Room.apply { colorResource(id = R.color.teal_200) },
                KeyboardType.Text
            ) {}
//            }
            val c = LocalContext.current
//            LaunchedEffect(key1 = source) {
//                val loc = getLocationFromAddress(c, source)
//                toast("$loc", c)
//            }
            Spacer(modifier = Modifier.height(10.dp))
            PermissionsRequired(
                multiplePermissionsState = multiplePermissionState,
                permissionsNotGrantedContent = { /* ... */ },
                permissionsNotAvailableContent = { /* ... */ }
            ) {
                if (Utils.locationEnabled(LocalContext.current)) {
                    viewModel.getCurrentLocation()
                } else {
                    viewModel.locationState = LocationState.LocationDisabled
                }

                AnimatedContent(targetState = viewModel.locationState) {
                    when(it){
                        is LocationState.NoPermission-> {
                            toast("no permission", LocalContext.current)
                        }
                        is LocationState.LocationDisabled->{
                            toast("disable", LocalContext.current)
                        }
                        is LocationState.LocationLoading->{
                            toast("loading", LocalContext.current)
                        }
                        is LocationState.LocationAvailable->{
                            toast("available", LocalContext.current)
                        }

                        is LocationState.Error -> {
                            toast("Error", LocalContext.current)
                        }
                    }

                }
//                AnimatedContent(viewModel.locationState) { state ->
//
//                    when (state) {
//                        is LocationState.NoPermission -> {
//
//                        }
//
//                        is LocationState.LocationDisabled -> {
//                            var openDialog by remember {
//                                mutableStateOf(true) // Initially dialog is closed
//                            }
//
//                            DialogBox(openSetting = {
//                                requestLocationEnable(
//                                    this@Google_Map,
//                                    viewModel
//                                )
//                            }) {
//                                openDialog = false
//                            }
//                        }
//
//                        is LocationState.LocationLoading -> {
//
//                        }
//
//                        is LocationState.Error -> {
//
//                        }
//                        is LocationState.LocationAvailable -> {
//                            toast(state.location.toString(), LocalContext.current)
//                            GoogleMap(
//                                cameraPositionState = cameraPositionState,
//                                modifier = Modifier
//                                    .weight(1f)
//                                    .clip(shape = RoundedCornerShape(20.dp)),
//                                uiSettings = MapUiSettings(compassEnabled = true, zoomControlsEnabled = false)
//                            ){
//                                Marker(
//                                    state = rememberMarkerState(position = state.location),
//                                    title = "Your Location",
//                                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
//                                )
//                            }
//                        }
//                    }
//                }
//                GoogleMap(
//                    cameraPositionState = cameraPositionState,
//                    modifier = Modifier
//                        .weight(1f)
//                        .clip(shape = RoundedCornerShape(20.dp)),
////                    properties = MapProperties(isMyLocationEnabled = true),
//                    uiSettings = MapUiSettings(compassEnabled = false, zoomControlsEnabled = false)
//                ) {
//                    Route()
//                    GoogleMarkers()
//                    Polyline(
//                        points = listOf(
//                            LatLng(44.811058, 20.4617586),
//                            LatLng(44.811058, 20.4627586),
//                            LatLng(44.810058, 20.4627586),
//                            LatLng(44.809058, 20.4627586),
//                            LatLng(44.809058, 20.4617586)
//                        )
//                    )
//                    Polyline(lineoption)
//                }
            }
        }
    }


    @Composable
    fun GoogleMarkers() {
        Marker(
            state = rememberMarkerState(position = LatLng(44.811058, 20.4617586)),
            title = "Marker1",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        )
//        Marker(
//            state = rememberMarkerState(position = LatLng(44.811058, 20.4627586)),
//            title = "Marker2",
//            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
//        )
//        Marker(
//            state = rememberMarkerState(position = LatLng(44.810058, 20.4627586)),
//            title = "Marker3",
//            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
//        )
//        Marker(
//            state = rememberMarkerState(position = LatLng(44.809058, 20.4627586)),
//            title = "Marker4",
//            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
//        )
//        Marker(
//            state = rememberMarkerState(position = LatLng(44.809058, 20.4617586)),
//            title = "Marker5",
//            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)
//        )
    }

    @Composable
    fun getRoute() {
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }
        LaunchedEffect(key1 = destination) {
//            Route()
        }
        GoogleMap() {

        }
    }

    @Composable
    fun Route() {
        val geoApiContext = GeoApiContext.Builder()
            .apiKey(apiKey)
            .build()
        val Source = getLoc(geoApiContext, source)
        val Destination = getLoc(geoApiContext, destination)
        val OrigLoc = LatLng(Source!!.first, Source.second)
        val desLoc = LatLng(Destination!!.first, Destination.second)
        Marker(
            state = rememberMarkerState(position = OrigLoc),
            title = "Your Location",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        )
        Marker(
            state = rememberMarkerState(position = desLoc),
            title = "Marker5",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        )
        Polyline(points = listOf(OrigLoc, desLoc))
//        val url=getDirectionURL(OrigLoc,desLoc)

    }

    fun getLoc(geoApiContext: GeoApiContext, address: String): Pair<Double, Double>? {
        val results: Array<GeocodingResult> =
            GeocodingApi.geocode(geoApiContext, address).await()
        if (results.isNotEmpty()) {
            val location = results[0].geometry.location
            return Pair(location.lat, location.lng)
        }
        return null
    }

    private fun getDirectionURL(origin: LatLng, dest: LatLng): String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" +
                "&destination=${dest.latitude},${dest.longitude}" +
                "&sensor=false" +
                "&mode=driving" +
                "&key=$apiKey"
    }


    private inner class GetDirection(val url: String) :
        AsyncTask<Void, Void, List<List<LatLng>>>() {
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()

            val result = ArrayList<List<LatLng>>()
            try {
                val respObj = Gson().fromJson(data, Common.MapData::class.java)
                val path = ArrayList<LatLng>()
                for (i in 0 until respObj.routes[0].legs[0].steps.size) {
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {

            for (i in result.indices) {
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(android.graphics.Color.BLUE)
                lineoption.geodesic(true)
            }

        }
    }

    fun decodePolyline(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
            poly.add(latLng)
        }
        return poly
    }

    fun getLocationFromAddress(context: Context, strAddress: String): LatLng? {
        val coder = Geocoder(context)
        var address: List<Address>?
        var p1: LatLng? = null

        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location: Address = address[0]
            val latitude = location.latitude
            val longitude = location.longitude

            p1 = LatLng(latitude, longitude)

            return p1
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}


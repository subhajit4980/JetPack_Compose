package com.example.jetpack_compose.Graph

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.jetpack_compose.R
import com.example.jetpack_compose.ui.theme.Jetpack_composeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Graph : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jetpack_composeTheme(dynamicColor = true) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MyScaffoldLayout()
                }
            }
        }
    }
@Preview(showBackground = true)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyScaffoldLayout() {
        val scaffoldState = rememberScaffoldState()
        val corotineScope= rememberCoroutineScope()
        val context= LocalContext.current.applicationContext
        Scaffold() {
            Column(
                modifier=Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Column(
                    modifier = Modifier.rotate(-180.0F),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    val list= listOf(1,2,3,4,6,2,3,1)
                    LazyRow(modifier=Modifier.padding(all=10.dp), userScrollEnabled = true){
                        items(list) {  item->
//                            RotatedBox(item,rotationAngle = 180f){
                            Box(modifier = Modifier
                                .height(item*20.dp)
                                .width(5.dp)
                                .background(Color.Red))
//                            }
                            Spacer(modifier = Modifier.padding(all = 5.dp))
                        }
                    }
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    val list= listOf(1,2,3,4,6,2,3,1).reversed()
                    LazyRow(modifier=Modifier.padding(all=10.dp), userScrollEnabled = true){
                        items(list) {  item->
//                            RotatedBox(item,rotationAngle = 180f){
                            Text("$item")
                            Spacer(modifier = Modifier.padding(all = 8.dp))
                        }
                    }
                }
            }

        }
    }

    @SuppressLint("ComposableNaming")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
     fun rememberScaffoldState(
        drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
        snakbarHostState: SnackbarHostState = remember {
            SnackbarHostState()
        }
    ){}

}


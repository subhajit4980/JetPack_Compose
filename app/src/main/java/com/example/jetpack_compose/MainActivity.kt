package com.example.jetpack_compose

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint.Style
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpack_compose.ColorPicker.ImageColorPicker
import com.example.jetpack_compose.Component.ExpandableCard
import com.example.jetpack_compose.Component.ItemLayout
import com.example.jetpack_compose.Component.prepareOptionsList
import com.example.jetpack_compose.Darktheme.ThemeSwitch
import com.example.jetpack_compose.Darktheme.ThemeViewModel
import com.example.jetpack_compose.Graph.Graph
import com.example.jetpack_compose.Image_Picker.Gallary_Camera
import com.example.jetpack_compose.Payment.RazorPay
import com.example.jetpack_compose.ui.theme.Jetpack_composeTheme
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text
import java.time.format.TextStyle

@SuppressLint("StaticFieldLeak")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Jetpack_composeTheme(dynamicColor = true) {
                val themeViewModel: ThemeViewModel = hiltViewModel()
                val themeState by themeViewModel.themeState.collectAsState()
                Surface{
                    Component.darkmode(themeState.isDarkMode, LocalContext.current)
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        MyUI()
                        ThemeSwitch()
                        Button(onClick = { startActivity(Intent(this@MainActivity, Gallary_Camera::class.java)) }) {
                            Text("Image Picker")
                        }
                        Button(onClick = { startActivity(Intent(this@MainActivity, ImageColorPicker::class.java)) }) {
                            Text("Color Picker")
                        }
                        Button(onClick = { startActivity(Intent(this@MainActivity, RazorPay::class.java)) }) {
                            Text("Payment")
                        }
                        Button(onClick = { startActivity(Intent(this@MainActivity, Graph::class.java)) }) {
                            Text("Payment")
                        }
                    }
                }
            }
        }
    }
}@Composable
fun MyUI() {
    var switchON by remember {
        mutableStateOf(false)
    }

    Switch(
        checked = switchON,
        onCheckedChange = { switchState ->
            switchON = switchState
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MAINUI() {
    val context = LocalContext.current.applicationContext
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {
//        Component().CardView(id = R.drawable.layer, text = "subhajit", context)
//        Component().ExpandableCard(
//            title = "My Title",
//            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
//                    "sed do eiusmod tempor incididunt ut labore et dolore magna " +
//                    "aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
//                    "ullamco laboris nisi ut aliquip ex ea commodo consequat."
//        )
        val optionsList = prepareOptionsList()

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(space = 24.dp), // gap between items
            contentPadding = PaddingValues(all = 22.dp) // padding for LazyColumn layout
        ) {
            items(optionsList) { item ->
                ItemLayout(optionsList = item)
            }
            items(4) {
                ExpandableCard(
                    title = "My Title",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                            "sed do eiusmod tempor incididunt ut labore et dolore magna " +
                            "aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
                            "ullamco laboris nisi ut aliquip ex ea commodo consequat."
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UI() {
    val context = LocalContext.current.applicationContext
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.reading),
            contentDescription = "dummy",
//            colorFilter = ColorFilter.tint(Color.Black),
            contentScale = ContentScale.Crop
        )
        Text(
            "hello",
            color = Color.Green,
            fontStyle = FontStyle.Italic,
            fontSize = 36.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.padding(20.dp)
        )
//        Text("Subhajit")
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text(text = "Hi")
//            Text(" Sudip")
//        }
        Button(
            onClick = { }, colors = ButtonDefaults.buttonColors(
                contentColor = Color.Green,
                containerColor = Color.Blue
            )
        ) {
            Text(text = "Submit")
        }
//       textInput
        val state = remember { mutableStateOf("") }
        OutlinedTextField(
            value = state.value,
            onValueChange = { state.value = it },
            label = { Text("Enter your Name") },
            placeholder = { Text(text = "Subhajit", fontStyle = FontStyle.Italic) },
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 20.sp),
            maxLines = 1,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_person_24),
                    contentDescription = ""
                )
            },
            shape = RoundedCornerShape(CornerSize(50.dp)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
//                containerColor = Color.Green.copy(alpha = 0.4f),
                cursorColor = Color.Yellow,
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Red
            ),
//            modifier = Modifier.background(
//                brush = Brush.horizontalGradient(
//                    listOf(
//                        Color.Green,
//                        Color.Yellow
//                    )
//                ),
//                shape=RoundedCornerShape(CornerSize(50.dp)),
//            )
            modifier = Modifier.fillMaxWidth(.8f),
            keyboardOptions = KeyboardOptions(
// This is the type of keyboard, for example, text or number pad. Available options:
                keyboardType = KeyboardType.Text,
// it signals the keyboard what type of action should be displayed. The Enter key on the keyboard is changed according to the action.
                imeAction = ImeAction.Done
            ),
// It is a lambda that gets called when the above imeAction is triggered.
            keyboardActions = KeyboardActions(
                onDone = {
                    Toast.makeText(
                        context,
                        "On Done Click: value = ${state.value}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            )
        )

    }

//    val topAppBarScaffoldState = rememberTopAppBarState()
//    TopAppBar(
//        title = { Text(text = "BOOKHUB") },
//        modifier = Modifier.background(Color.Red),
//        navigationIcon = {
//            R.drawable.ic_launcher_background
//        },
//        actions = {
//
//        }
//    )
}


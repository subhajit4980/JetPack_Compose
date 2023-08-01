package com.example.jetpack_compose

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

class Gallary_Camera : ComponentActivity() {
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .background(
                        Color.White
                    )
            ) {
                context = LocalContext.current.applicationContext
                UI()

            }

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview(showBackground = true)
    @Composable
    private fun UI() {
//        image from gallery
        var selectimageuri by remember {
            mutableStateOf<Uri?>(null)
        }
        val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                selectimageuri = uri
            }
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Card(
                elevation = CardDefaults.cardElevation(10.dp),
                modifier = Modifier
                    .height(200.dp)
                    .width(300.dp),
                onClick = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            ) {
                AsyncImage(
                    model = selectimageuri,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(1f),
                    contentScale = ContentScale.FillBounds,
                    placeholder = painterResource(
                        id = R.drawable.layer
                    )
                )
            }
            Spacer(modifier = Modifier.padding(all = 10.dp))
//            capture image from camera
            var capturedImagebitmap by remember {
                mutableStateOf<Bitmap?>(null)
            }

            val cameraLauncher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { bitmap ->
                    capturedImagebitmap = bitmap
                }
            Card(
                elevation = CardDefaults.cardElevation(10.dp),
                modifier = Modifier
                    .height(200.dp)
                    .width(300.dp),
                onClick = {
                    cameraLauncher.launch()
                }
            ) {
                capturedImagebitmap?.let {
                    Image(
                        bitmap = capturedImagebitmap?.asImageBitmap()!!,
                        contentDescription = "camera image",
                        modifier = Modifier.fillMaxSize(1f),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }

        }
    }


}


//@SuppressLint("SimpleDateFormat")
//fun Context.createImageFile(): File {
//    // Create an image file name
//    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//    val imageFileName = "JPEG_" + timeStamp + "_"
//    val image = File.createTempFile(
//        imageFileName, /* prefix */
//        ".jpg", /* suffix */
//        externalCacheDir      /* directory */
//    )
//    return image
//}
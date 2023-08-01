package com.example.jetpack_compose.ColorPicker
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetpack_compose.Constants.IMAGE_1

class PaletteViewModel: ViewModel() {

    private val _colorPalette: MutableState<Map<String,String>> = mutableStateOf(mapOf())
    val colorPalette: State<Map<String,String>> = _colorPalette

    fun setColorPalette(colors: Map<String,String>) {
        _colorPalette.value = colors
    }

    var imageUrl: MutableState<String> = mutableStateOf(IMAGE_1)

}
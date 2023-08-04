package com.example.jetpack_compose.Darktheme

import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.example.jetpack_compose.Darktheme.DataStoreUtil.Companion.IS_DARK_MODE_KEY
import kotlinx.coroutines.Dispatchers
import com.example.jetpack_compose.Darktheme.DataStoreUtil
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(dataStoreUtil:DataStoreUtil):ViewModel() {
    private val _themeState= MutableStateFlow(ThemeState(false))
    val themeState:StateFlow<ThemeState> = _themeState
    private val dataStore=dataStoreUtil.dataStore

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preference->
                ThemeState(preference[IS_DARK_MODE_KEY]?:false)
            }.collect {
                _themeState.value = it
            }
        }
    }
    fun toggleTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[IS_DARK_MODE_KEY] = !(preferences[IS_DARK_MODE_KEY] ?: false)
            }
        }
    }
}
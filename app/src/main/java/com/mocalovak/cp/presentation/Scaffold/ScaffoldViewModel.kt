package com.mocalovak.cp.presentation.Scaffold

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mocalovak.cp.data.local.preferences.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScaffoldViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
):ViewModel()
{
    private val _lastCharacterId = MutableStateFlow(0)
    val lastCharacterId: StateFlow<Int> = _lastCharacterId

    init {
        viewModelScope.launch(Dispatchers.IO) {
            preferenceManager.lastCharacterId.collect { id ->
                _lastCharacterId.value = id
            }
        }
    }

    fun removeLastCharacterId() {
        viewModelScope.launch(Dispatchers.IO) {
            preferenceManager.deleteLastCharacterId()
            _lastCharacterId.value = 0
        }
    }
}
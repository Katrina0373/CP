package com.mocalovak.cp.presentation.Scaffold

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mocalovak.cp.data.local.preferences.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScaffoldViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
):ViewModel()
{
    val lastCharacterId: StateFlow<String> = preferenceManager.lastCharacterId
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "null")

    fun removeLastCharacterId(){
        viewModelScope.launch(Dispatchers.IO) {
            preferenceManager.deleteLastCharacterId()
        }
    }
}
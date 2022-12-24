package com.jonesl7l.clubsProCreator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import com.jonesl7l.clubsProCreator.data.LoginRepository
import com.jonesl7l.clubsProCreator.data.VirtualProRepository
import com.jonesl7l.clubsProCreator.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class VirtualProGlobalViewModel @Inject constructor(private val repository: VirtualProRepository) : ViewModel() {

    private var _currentGlobalProStats: MutableStateFlow<GlobalVirtualProOptions?> = MutableStateFlow(GlobalVirtualProOptions())

    fun getCurrentGlobalProStats(): StateFlow<GlobalVirtualProOptions?> = _currentGlobalProStats

    fun fetchGlobalProStats() {
        viewModelScope.launch {
            repository.fetchGlobalProStats().collect {
                when(it) {
                    is RestResult.Success -> {
                        _currentGlobalProStats.value = it.data
                    }
                    is RestResult.ApiError -> {
                        _currentGlobalProStats.value = null
                    }
                    else -> {}
                }
            }
        }
    }
}
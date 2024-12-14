package com.example.graphapp.fragment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphapp.data.repository.PointsRepository
import com.example.graphapp.data.entity.Point
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PointsRepository) : ViewModel() {
    private val _state = MutableLiveData<UIState>(UIState.Pending)
    val state: LiveData<UIState> = _state

    fun loadPoints(count: Int) {
        viewModelScope.launch {
            _state.value = UIState.Loading
            val result = repository.fetchPoints(count)
            _state.value = result.fold(
                onSuccess = { UIState.Success(it) },
                onFailure = { UIState.Error(it) }
            )
        }
    }

    fun clearState() {
        _state.value = UIState.Pending
    }
}

sealed class UIState {
    object Loading : UIState()
    object Pending : UIState()
    data class Success(val points: List<Point>) : UIState()
    data class Error(val error: Throwable) : UIState()
}
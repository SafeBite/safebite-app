package com.celvine.deb.esail.bby.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celvine.deb.esail.bby.common.UiState
import com.celvine.deb.esail.bby.data.model.FoodIdResponse
import com.celvine.deb.esail.bby.data.repositories.FoodDetailRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodDetailViewModel(private val repository: FoodDetailRepo) : ViewModel() {

    private val _foodDetailState = MutableStateFlow<UiState<FoodIdResponse>>(UiState.Loading)
    val foodDetailState: StateFlow<UiState<FoodIdResponse>> = _foodDetailState

    fun getFoodDetail(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getFoodDetail(id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _foodDetailState.value = UiState.Success(it)
                    } ?: run {
                        _foodDetailState.value = UiState.Error("No data found")
                    }
                } else {
                    _foodDetailState.value = UiState.Error(response.message())
                }
            } catch (e: Exception) {
                _foodDetailState.value = UiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}

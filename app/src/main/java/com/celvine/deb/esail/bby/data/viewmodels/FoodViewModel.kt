package com.celvine.deb.esail.bby.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celvine.deb.esail.bby.common.UiState
import com.celvine.deb.esail.bby.data.model.Food
import com.celvine.deb.esail.bby.data.repositories.FoodRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodViewModel(private val repository: FoodRepo) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Food>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Food>>> = _uiState

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    init {
        getAllFoods()
    }

    fun getAllFoods() {
        _uiState.value = UiState.Loading
        repository.getFoods(
            onSuccess = {
                _uiState.value = UiState.Success(repository.foods)
            },
            onError = { error ->
                _uiState.value = UiState.Error(error)
            }
        )
    }

    fun search(query: String) {
        _query.value = query
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getFoods(
                onSuccess = {
                    val filteredFoods = repository.foods.filter { it.name.contains(query, true) }
                    _uiState.value = UiState.Success(filteredFoods)
                },
                onError = { error ->
                    _uiState.value = UiState.Error(error)
                }
            )
        }
    }

    fun removeQuery() {
        _query.value = ""
        getAllFoods()
    }
}

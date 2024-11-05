package com.alba.filme_in.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alba.filme_in.data.models.ApiResponse
import com.alba.filme_in.data.repository.FilmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilmViewModel : ViewModel() {
    private val repository = FilmRepository()

    private val _popularPeople = MutableStateFlow<ApiResponse?>(null)
    val popularPeople: StateFlow<ApiResponse?> get() = _popularPeople

    fun fetchPopularPeople(language: String = "en-US", page: Int = 1) {
        viewModelScope.launch {
            _popularPeople.value = repository.fetchPopularPeople(language, page)
        }
    }
}

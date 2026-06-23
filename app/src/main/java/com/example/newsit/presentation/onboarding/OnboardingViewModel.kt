package com.example.newsit.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newsit.data.onboarding.OnboardingRepositoryImpl
import com.example.newsit.domain.onboarding.model.OnboardingPage
import com.example.newsit.domain.onboarding.usecase.GetOnboardingPagesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OnboardingUiState(
    val pages: List<OnboardingPage> = emptyList(),
    val currentPage: Int = 0
) {
    val isLastPage: Boolean get() = currentPage == pages.size - 1
    val currentPageData: OnboardingPage? get() = pages.getOrNull(currentPage)
    val progress: Float get() = if (pages.isEmpty()) 0f else (currentPage + 1).toFloat() / pages.size
}

class OnboardingViewModel(
    private val getOnboardingPagesUseCase: GetOnboardingPagesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingUiState())
    val state: StateFlow<OnboardingUiState> = _state.asStateFlow()

    init {
        loadPages()
    }

    private fun loadPages() {
        viewModelScope.launch {
            val pages = getOnboardingPagesUseCase()
            _state.update { it.copy(pages = pages) }
        }
    }

    fun onNextPage() {
        _state.update { state ->
            if (state.currentPage < state.pages.size - 1) {
                state.copy(currentPage = state.currentPage + 1)
            } else state
        }
    }

    fun onPreviousPage() {
        _state.update { state ->
            if (state.currentPage > 0) {
                state.copy(currentPage = state.currentPage - 1)
            } else state
        }
    }

    fun onPageSelected(page: Int) {
        _state.update { it.copy(currentPage = page.coerceIn(0, it.pages.size - 1)) }
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = OnboardingRepositoryImpl()
                val useCase = GetOnboardingPagesUseCase(repository)
                return OnboardingViewModel(useCase) as T
            }
        }
    }
}

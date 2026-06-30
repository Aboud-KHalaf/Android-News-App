package com.example.newsit.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsit.domain.onboarding.model.OnboardingPage
import com.example.newsit.domain.onboarding.usecase.CheckOnboardingCompletionUseCase
import com.example.newsit.domain.onboarding.usecase.GetOnboardingPagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

 data class OnboardingUiState(
     val pages: List<OnboardingPage> = emptyList(),
     val currentPage: Int = 0,
     val shouldShowOnboarding: Boolean = true
 ) {
     val isLastPage: Boolean get() = currentPage == pages.size - 1
     val currentPageData: OnboardingPage? get() = pages.getOrNull(currentPage)
     val progress: Float get() = if (pages.isEmpty()) 0f else (currentPage + 1).toFloat() / pages.size
 }

 @HiltViewModel
 class OnboardingViewModel @Inject constructor(
     private val getOnboardingPagesUseCase: GetOnboardingPagesUseCase,
     private val checkOnboardingCompletionUseCase: CheckOnboardingCompletionUseCase
 ) : ViewModel() {

     private val _state = MutableStateFlow(OnboardingUiState())
     val state: StateFlow<OnboardingUiState> = _state.asStateFlow()

     init {
         loadOnboardingState()
         loadPages()
     }

     private fun loadOnboardingState() {
         viewModelScope.launch {
             checkOnboardingCompletionUseCase().collect { isCompleted ->
                 _state.update { it.copy(shouldShowOnboarding = !isCompleted) }
             }
         }
     }

     private fun loadPages() {
         viewModelScope.launch {
             val pages = getOnboardingPagesUseCase()
             _state.update { it.copy(pages = pages) }
         }
     }

     fun onPageSelected(page: Int) {
         _state.update { it.copy(currentPage = page.coerceIn(0, it.pages.size - 1)) }
     }
 }

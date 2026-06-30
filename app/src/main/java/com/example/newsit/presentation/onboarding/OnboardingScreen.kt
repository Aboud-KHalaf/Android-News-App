package com.example.newsit.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

// ============================================================
// File: OnboardingScreen.kt
// Top-level screen composable. Owns pager state, scroll
// coroutine scope, and assembles the sub-sections.
// ============================================================
@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {

    // by : Property Delegation
    // we can use = but then we need to access value to access the right one like text.value.somthing
    val state by viewModel.state.collectAsStateWithLifecycle()

    // If onboarding has already been completed, skip the onboarding flow
    if (!state.shouldShowOnboarding) {
        onComplete()
        return
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { state.pages.size }
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        viewModel.onPageSelected(pagerState.currentPage)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
                        MaterialTheme.colorScheme.background
                    ),
                    startY = 0f,
                    endY = 900f
                )
            )
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        SkipButton(
            show = !state.isLastPage,
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(state.pages.size - 1)
                }
            }
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            userScrollEnabled = true
        ) { page ->
            OnboardingPageContent(
                pageData = state.pages[page],
                modifier = Modifier.fillMaxSize()
            )
        }

        PageIndicator(
            pageCount = state.pages.size,
            currentPage = state.currentPage,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        BottomButtons(
            isLastPage = state.isLastPage,
            isFirstPage = state.currentPage == 0,
            onNext = {
                scope.launch {
                    pagerState.animateScrollToPage(state.currentPage + 1)
                }
            },
            onPrevious = {
                scope.launch {
                    pagerState.animateScrollToPage(state.currentPage - 1)
                }
            },
            onComplete = onComplete,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp)
        )
    }
}

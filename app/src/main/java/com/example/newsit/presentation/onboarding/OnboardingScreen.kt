package com.example.newsit.presentation.onboarding

import androidx.compose.animation.core.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    viewModel: OnboardingViewModel = viewModel(factory = OnboardingViewModel.factory)
) {
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { state.pages.size }
    )
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        SkipButton(
            show = !state.isLastPage,
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(state.pages.size - 1)
                    viewModel.onPageSelected(state.pages.size - 1)
                }
            }
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            userScrollEnabled = true,
            beyondBoundsPageCount = 0
        ) { page ->
            OnboardingPageContent(
                pageData = state.pages[page],
                modifier = Modifier.fillMaxSize()
            )
        }

        PageIndicator(
            pageCount = state.pages.size,
            currentPage = state.currentPage
        )

        BottomButtons(
            isLastPage = state.isLastPage,
            isFirstPage = state.currentPage == 0,
            onNext = {
                scope.launch {
                    pagerState.animateScrollToPage(state.currentPage + 1)
                }
                viewModel.onNextPage()
            },
            onPrevious = {
                scope.launch {
                    pagerState.animateScrollToPage(state.currentPage - 1)
                }
                viewModel.onPreviousPage()
            },
            onComplete = onComplete,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp)
        )
    }
}

@Composable
private fun SkipButton(show: Boolean, onClick: () -> Unit) {
    if (show) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onClick) {
                Text("Skip")
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(
    pageData: com.example.newsit.domain.onboarding.model.OnboardingPage,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = pageData.icon,
                contentDescription = pageData.title,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = pageData.title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = pageData.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun PageIndicator(
    pageCount: Int,
    currentPage: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage
            val color by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outlineVariant,
                animationSpec = tween(300),
                label = "dotColor"
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(if (isSelected) 10.dp else 8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Composable
private fun BottomButtons(
    isLastPage: Boolean,
    isFirstPage: Boolean,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isFirstPage) {
            Spacer(modifier = Modifier.size(80.dp))
        } else {
            TextButton(onClick = onPrevious) {
                Text("Back")
            }
        }

        if (isLastPage) {
            Button(
                onClick = onComplete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Get Started")
            }
        } else {
            Button(
                onClick = onNext,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Next")
            }
        }
    }
}

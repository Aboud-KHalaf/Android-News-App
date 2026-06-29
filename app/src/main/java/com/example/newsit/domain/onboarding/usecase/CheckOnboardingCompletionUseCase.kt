package com.example.newsit.domain.onboarding.usecase

import com.example.newsit.domain.onboarding.repository.OnboardingRepository
import javax.inject.Inject

class CheckOnboardingCompletionUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    operator fun invoke(): kotlinx.coroutines.flow.Flow<Boolean> = repository.isOnboardingCompleted()
}
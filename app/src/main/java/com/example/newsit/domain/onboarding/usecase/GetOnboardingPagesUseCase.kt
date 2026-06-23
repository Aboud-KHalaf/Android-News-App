package com.example.newsit.domain.onboarding.usecase

import com.example.newsit.domain.onboarding.model.OnboardingPage
import com.example.newsit.domain.onboarding.repository.OnboardingRepository

class GetOnboardingPagesUseCase(
    private val repository: OnboardingRepository
) {
    suspend operator fun invoke(): List<OnboardingPage> {
        return repository.getOnboardingPages()
    }
}

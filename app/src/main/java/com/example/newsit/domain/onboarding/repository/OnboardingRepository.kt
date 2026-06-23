package com.example.newsit.domain.onboarding.repository

import com.example.newsit.domain.onboarding.model.OnboardingPage

interface OnboardingRepository {
    suspend fun getOnboardingPages(): List<OnboardingPage>
}

package com.example.newsit.domain.onboarding.repository

import com.example.newsit.domain.onboarding.model.OnboardingPage
import kotlinx.coroutines.flow.Flow

interface OnboardingRepository {
    suspend fun getOnboardingPages(): List<OnboardingPage>
    fun setOnboardingCompleted(completed: Boolean)
    fun isOnboardingCompleted(): Flow<Boolean>
}

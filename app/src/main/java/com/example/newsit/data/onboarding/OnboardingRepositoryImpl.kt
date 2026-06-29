package com.example.newsit.data.onboarding

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import com.example.newsit.domain.onboarding.model.OnboardingPage
import com.example.newsit.domain.onboarding.repository.OnboardingRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : OnboardingRepository {
    override suspend fun getOnboardingPages(): List<OnboardingPage> {
        return listOf(
            OnboardingPage(
                title = "Breaking News",
                description = "Get the latest breaking news from around the world. Stay informed with real-time updates.",
                icon = Icons.Filled.Notifications
            ),
            OnboardingPage(
                title = "Personalized Feed",
                description = "Follow topics that matter to you. Curate your news feed based on your interests.",
                icon = Icons.Filled.Star
            ),
            OnboardingPage(
                title = "In-Depth Analysis",
                description = "Go beyond the headlines. Access expert opinions and comprehensive coverage.",
                icon = Icons.Filled.Info
            ),
            OnboardingPage(
                title = "Get Started",
                description = "Your journey to staying informed starts now. Explore the world with NewsIt.",
                icon = Icons.Filled.Check
            )
        )
    }

    override fun setOnboardingCompleted(completed: Boolean) {
        context.getSharedPreferences("NewsItPrefs", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("onboardingCompleted", completed)
            .apply()
    }

    override fun isOnboardingCompleted(): kotlinx.coroutines.flow.Flow<Boolean> {
        val sharedPrefs = context.getSharedPreferences("NewsItPrefs", Context.MODE_PRIVATE)
        val initialValue = sharedPrefs.getBoolean("onboardingCompleted", false)
        return kotlinx.coroutines.flow.callbackFlow {
            val listener = android.content.SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == "onboardingCompleted") {
                    trySend(sharedPrefs.getBoolean(key, false))
                }
            }
            sharedPrefs.registerOnSharedPreferenceChangeListener(listener)
            trySend(initialValue)
            awaitClose { sharedPrefs.unregisterOnSharedPreferenceChangeListener(listener) }
        }
    }
}
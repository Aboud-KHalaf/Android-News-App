package com.example.newsit.di

import com.example.newsit.data.home.repository.ArticleRepositoryImpl
import com.example.newsit.data.onboarding.OnboardingRepositoryImpl
import com.example.newsit.domain.home.repository.ArticleRepository
import com.example.newsit.domain.onboarding.repository.OnboardingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindOnboardingRepository(
        impl: OnboardingRepositoryImpl
    ): OnboardingRepository

    @Binds
    abstract fun bindArticleRepository(
        impl: ArticleRepositoryImpl
    ): ArticleRepository
}

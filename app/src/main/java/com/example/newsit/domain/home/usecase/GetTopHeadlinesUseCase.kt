package com.example.newsit.domain.home.usecase

import com.example.newsit.domain.home.model.Article
import com.example.newsit.domain.home.repository.ArticleRepository
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(country: String = "us"): List<Article> {
        return repository.getTopHeadlines(country)
    }
}

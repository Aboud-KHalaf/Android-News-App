package com.example.newsit.domain.home.usecase

import com.example.newsit.domain.home.model.Article
import com.example.newsit.domain.home.repository.ArticleRepository
import javax.inject.Inject

class SearchArticlesUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(query: String): List<Article> {
        return repository.searchArticles(query)
    }
}

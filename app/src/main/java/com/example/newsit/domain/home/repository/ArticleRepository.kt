package com.example.newsit.domain.home.repository

import com.example.newsit.domain.home.model.Article

interface ArticleRepository {
    suspend fun getTopHeadlines(country: String = "us"): List<Article>
    suspend fun searchArticles(query: String): List<Article>
}

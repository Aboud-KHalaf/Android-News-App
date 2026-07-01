package com.example.newsit.data.home.repository

import com.example.newsit.BuildConfig
import com.example.newsit.data.home.model.ArticleDto
import com.example.newsit.data.home.remote.NewsApiService
import com.example.newsit.domain.home.model.Article
import com.example.newsit.domain.home.model.Source
import com.example.newsit.domain.home.repository.ArticleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepositoryImpl @Inject constructor(
    private val apiService: NewsApiService
) : ArticleRepository {

    override suspend fun getTopHeadlines(country: String): List<Article> {
        val response = apiService.getTopHeadlines(
            country = country,
            apiKey = BuildConfig.NEWS_API_KEY
        )
        return response.articles.map { it.toDomain() }
    }

    override suspend fun searchArticles(query: String): List<Article> {
        val response = apiService.searchArticles(
            query = query,
            apiKey = BuildConfig.NEWS_API_KEY
        )
        return response.articles.map { it.toDomain() }
    }

    private fun ArticleDto.toDomain(): Article {
        return Article(
            source = Source(
                id = source.id,
                name = source.name
            ),
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content
        )
    }
}

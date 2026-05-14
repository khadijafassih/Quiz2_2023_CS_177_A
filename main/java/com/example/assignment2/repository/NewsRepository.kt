package com.example.assignment2.repository

import com.example.assignment2.api.RetrofitInstance
import com.example.assignment2.model.NewsResponse
import com.example.assignment2.utils.Config
import retrofit2.Response

class NewsRepository {
    suspend fun getTopHeadlines(countryCode: String): Response<NewsResponse> {
        return RetrofitInstance.api.getTopHeadlines(
            country = countryCode,
            apiKey = Config.API_KEY
        )
    }
}

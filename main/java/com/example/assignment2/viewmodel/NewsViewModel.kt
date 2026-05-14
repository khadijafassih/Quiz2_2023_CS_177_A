package com.example.assignment2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment2.model.Article
import com.example.assignment2.repository.NewsRepository
import kotlinx.coroutines.launch
import org.json.JSONObject

sealed class NewsState {
    object Loading : NewsState()
    data class Success(val articles: List<Article>) : NewsState()
    data class Error(val message: String) : NewsState()
}

class NewsViewModel : ViewModel() {
    private val repository = NewsRepository()
    val newsState = MutableLiveData<NewsState>()

    fun fetchNews(countryCode: String) {
        newsState.value = NewsState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getTopHeadlines(countryCode)
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    if (articles.isEmpty()) {
                        newsState.value = NewsState.Error("No news available for this country.")
                    } else {
                        newsState.value = NewsState.Success(articles)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        // GNews returns errors in a "errors" array or "message" field
                        val json = JSONObject(errorBody ?: "")
                        if (json.has("errors")) {
                            json.getJSONArray("errors").getString(0)
                        } else {
                            json.optString("message", "API Error: ${response.code()}")
                        }
                    } catch (e: Exception) {
                        "Error ${response.code()}: ${response.message()}"
                    }
                    newsState.value = NewsState.Error(errorMessage)
                }
            } catch (e: Exception) {
                newsState.value = NewsState.Error("Network error: Please check your connection.")
            }
        }
    }
}

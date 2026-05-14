package com.example.assignment2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class NewsResponse(
    val totalArticles: Int,
    val articles: List<Article>
)

@Parcelize
data class Article(
    val title: String,
    val description: String?,
    val content: String?,
    val url: String,
    val image: String?,
    val publishedAt: String,
    val source: Source
) : Parcelable

@Parcelize
data class Source(
    val name: String,
    val url: String?
) : Parcelable

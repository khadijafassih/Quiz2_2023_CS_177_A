package com.example.assignment2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment2.databinding.ItemNewsBinding
import com.example.assignment2.model.Article
import com.example.assignment2.utils.DateUtils

class NewsAdapter(private val onArticleClick: (Article) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var articles: List<Article> = emptyList()

    fun setArticles(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.tvNewsTitle.text = article.title
            binding.tvNewsSource.text = article.source.name
            binding.tvPublishedAt.text = DateUtils.formatIsoDate(article.publishedAt)

            Glide.with(binding.root.context)
                .load(article.image)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .into(binding.ivNewsImage)

            binding.root.setOnClickListener {
                onArticleClick(article)
            }
        }
    }
}

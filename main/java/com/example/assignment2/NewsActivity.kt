package com.example.assignment2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment2.adapter.NewsAdapter
import com.example.assignment2.databinding.ActivityNewsBinding
import com.example.assignment2.viewmodel.NewsState
import com.example.assignment2.viewmodel.NewsViewModel

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    private val countries = mapOf(
        "Pakistan" to "pk",
        "United States" to "us",
        "United Kingdom" to "gb",
        "India" to "in",
        "Saudi Arabia" to "sa",
        "UAE" to "ae"
    )

    private var currentCountryCode = "us"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupDropdown()
        setupObservers()

        binding.btnRefresh.setOnClickListener {
            viewModel.fetchNews(currentCountryCode)
        }

        // Set default text for dropdown
        binding.actvCountry.setText("United States", false)
        
        // Initial fetch
        viewModel.fetchNews(currentCountryCode)
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter { article ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("article", article)
            startActivity(intent)
        }
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            adapter = newsAdapter
        }
    }

    private fun setupDropdown() {
        val countryList = countries.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, countryList)
        binding.actvCountry.setAdapter(adapter)

        // Ensure dropdown shows on click even if text is present
        binding.actvCountry.setOnClickListener {
            binding.actvCountry.showDropDown()
        }

        binding.actvCountry.setOnItemClickListener { parent, _, position, _ ->
            val selectedCountry = parent.getItemAtPosition(position) as String
            currentCountryCode = countries[selectedCountry] ?: "us"
            viewModel.fetchNews(currentCountryCode)
        }
    }

    private fun setupObservers() {
        viewModel.newsState.observe(this) { state ->
            when (state) {
                is NewsState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvNews.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
                is NewsState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvNews.visibility = View.VISIBLE
                    newsAdapter.setArticles(state.articles)
                }
                is NewsState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = state.message
                    // Also show a Toast for clarity
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

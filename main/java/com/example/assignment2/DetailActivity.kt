package com.example.assignment2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.assignment2.databinding.ActivityDetailBinding
import com.example.assignment2.model.Article

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article = intent.getParcelableExtra<Article>("article")

        article?.let {
            binding.tvDetailTitle.text = it.title
            binding.tvDetailSourceDate.text = "${it.source.name} | ${it.publishedAt}"
            binding.tvDetailDescription.text = it.description
            binding.tvDetailContent.text = it.content

            Glide.with(this)
                .load(it.image)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .into(binding.ivDetailImage)

            binding.btnReadFull.setOnClickListener { _ ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                startActivity(browserIntent)
            }
        }
    }
}

package com.example.appensiklopediaandnews

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)

        // Ambil data yang dikirim melalui Intent
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val url = intent.getStringExtra("url")
        val imageUrl = intent.getStringExtra("imageUrl")
        val source = intent.getStringExtra("source")
        val publishedAt = intent.getStringExtra("publishedAt")

        // Inisialisasi view
        val titleTextView: TextView = findViewById(R.id.detailNewsTitle)
        val descriptionTextView: TextView = findViewById(R.id.detailNewsDescription)
        val imageView: ImageView = findViewById(R.id.detailNewsImage)
        val sourceTextView: TextView = findViewById(R.id.detailNewsSource)
        val timeTextView: TextView = findViewById(R.id.detailNewsTime)
        val urlTextView: TextView = findViewById(R.id.detailNewsUrl)

        // Set data ke view
        titleTextView.text = title
        descriptionTextView.text = description
        sourceTextView.text = source
        timeTextView.text = publishedAt
        urlTextView.text = url

        // Load image (Glide or Picasso)
        Glide.with(this).load(imageUrl).into(imageView)

        // Optionally, handle click on the URL to open in a browser
        urlTextView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}

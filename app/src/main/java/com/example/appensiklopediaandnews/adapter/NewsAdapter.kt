package com.example.appensiklopediaandnews.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.appensiklopediaandnews.DetailNewsActivity
import com.example.appensiklopediaandnews.R
import com.example.appensiklopediaandnews.model.Article

class NewsAdapter(private val newsList: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.newsImage)
        val title: TextView = itemView.findViewById(R.id.newsTitle)
        val source: TextView = itemView.findViewById(R.id.newsSource)
//        val time: TextView = itemView.findViewById(R.id.newsTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]

        // Gunakan Glide untuk memuat gambar
        Glide.with(holder.itemView.context)
            .load(news.urlToImage)
            .apply(RequestOptions().placeholder(R.drawable.placeholder_image))
            .into(holder.image)

        holder.title.text = news.title
        holder.source.text = news.source.name
//        holder.time.text = formatTimeAgo(news.publishedAt)

        // Set OnClickListener untuk setiap item
        holder.itemView.setOnClickListener {
            // Intent untuk berpindah ke DetailNewsActivity
            val intent = Intent(holder.itemView.context, DetailNewsActivity::class.java)
            // Kirim data berita ke DetailNewsActivity
            intent.putExtra("title", news.title)
            intent.putExtra("description", news.description)
            intent.putExtra("url", news.url)
            intent.putExtra("imageUrl", news.urlToImage)
            intent.putExtra("source", news.source.name)
            intent.putExtra("publishedAt", news.publishedAt)
            // Mulai DetailNewsActivity
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = newsList.size

    // Fungsi untuk memformat waktu menjadi "8 jam yang lalu", dll.
    private fun formatTimeAgo(publishedAt: String): String {
        // Implementasikan logika untuk memformat waktu di sini
        // Misalnya menggunakan SimpleDateFormat untuk memformat tanggal ke "x jam yang lalu"
        return ""
    }
}

package com.example.appensiklopediaandnews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appensiklopediaandnews.adapter.NewsAdapter
import com.example.appensiklopediaandnews.api.NewsApiService
import com.example.appensiklopediaandnews.model.NewsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsApiService: NewsApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            (recyclerView.layoutManager as LinearLayoutManager).orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        // Inisialisasi Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/") // URL dasar News API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        newsApiService = retrofit.create(NewsApiService::class.java)

        // Lakukan request untuk mendapatkan berita
        val apiKey = "2afa48928646498cad35302e0a795575" // Ganti dengan API Key Anda
        val call = newsApiService.getGameNews(apiKey = apiKey)

        call.enqueue(object : retrofit2.Callback<NewsResponse> {
            override fun onFailure(call: retrofit2.Call<NewsResponse>, t: Throwable) {
                Log.e("NewsFragment", "Gagal mendapatkan berita", t)
            }

            override fun onResponse(
                call: retrofit2.Call<NewsResponse>,
                response: retrofit2.Response<NewsResponse>
            ) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    if (newsResponse != null) {
                        val filteredArticles = newsResponse.articles.filterNot { article ->
                            article.title == "[Removed]"
                        }
                        newsAdapter = NewsAdapter(filteredArticles)
                        recyclerView.adapter = newsAdapter
                    }
                } else {
                    Log.e("NewsFragment", "Gagal mendapatkan berita")
                }
            }
        })

        return view
    }
}
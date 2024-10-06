package com.example.appensiklopediaandnews.api

import com.example.appensiklopediaandnews.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/everything")
    fun getGameNews(
        @Query("q") query: String = "game", // Fokus pada berita game
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>
}
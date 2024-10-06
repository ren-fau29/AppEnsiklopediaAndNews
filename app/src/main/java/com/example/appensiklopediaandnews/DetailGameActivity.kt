package com.example.appensiklopediaandnews

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.appensiklopediaandnews.adapter.ImageSliderAdapter
import com.example.appensiklopediaandnews.model.Game
import com.squareup.picasso.Picasso

class DetailGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_game)
        val images = intent.getStringArrayListExtra("IMAGES") ?: listOf()

//        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
//        val adapter = ImageSliderAdapter(images)
//        viewPager.adapter = adapter

        val image = intent.getStringExtra("IMAGE")
        val title = intent.getStringExtra("TITLE")
        val description = intent.getStringExtra("DESCRIPTION")
        val genre = intent.getStringExtra("GENRE")
        val cpuMi = intent.getStringExtra("CPU_MI")
        val vgaMi = intent.getStringExtra("VGA_MI")
        val ramMi = intent.getStringExtra("RAM_MI")
        val storageMi = intent.getStringExtra("STORAGE_MI")
        val cpuRe = intent.getStringExtra("CPU_RE")
        val vgaRe = intent.getStringExtra("VGA_RE")
        val ramRe = intent.getStringExtra("RAM_RE")
        val storageRe = intent.getStringExtra("STORAGE_RE")
        val newsUrl = intent.getStringExtra("NEWS_URL")

        findViewById<ImageView>(R.id.Image)?.let {
            Picasso.get().load(image).into(it)
        }
        findViewById<TextView>(R.id.Title).text = title
        findViewById<TextView>(R.id.detailDescription).text = description
        findViewById<TextView>(R.id.genre).text = genre
        findViewById<TextView>(R.id.detailCPU_mi).text = cpuMi
        findViewById<TextView>(R.id.detailVGA_mi).text = vgaMi
        findViewById<TextView>(R.id.detailRAM_mi).text = ramMi
        findViewById<TextView>(R.id.detailStorage_mi).text = storageMi
        findViewById<TextView>(R.id.detailCPU_Re).text = cpuRe
        findViewById<TextView>(R.id.detailVGA_Re).text = vgaRe
        findViewById<TextView>(R.id.detailRAM_Re).text = ramRe
        findViewById<TextView>(R.id.detailStorage_Re).text = storageRe
        findViewById<TextView>(R.id.detailNewsUrl).text = newsUrl
    }
}
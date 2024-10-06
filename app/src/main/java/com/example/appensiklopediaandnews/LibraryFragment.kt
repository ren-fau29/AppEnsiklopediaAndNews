package com.example.appensiklopediaandnews

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appensiklopediaandnews.adapter.GameAdapter
import com.example.appensiklopediaandnews.model.Game
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.io.Serializable

class LibraryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = GameAdapter(emptyList())
        recyclerView.adapter = adapter

        // Ambil data dari Firestore
        val db = FirebaseFirestore.getInstance()
        db.collection("games").get().addOnSuccessListener { documents ->
            val games = documents.documents.map { document ->
                val image = document.getString("image") ?: ""
                val title = document.getString("title") ?: ""
                val genre = document.getString("genre") ?: ""
                val description = document.getString("description") ?: ""
                val cpuMi = document.getString("cpuMi") ?: ""
                val vgaMi = document.getString("vgaMi") ?: ""
                val ramMi = document.getString("ramMi") ?: ""
                val storageMi = document.getString("storageMi") ?: ""
                val cpuRe = document.getString("cpuRe") ?: ""
                val vgaRe = document.getString("vgaRe") ?: ""
                val ramRe = document.getString("ramRe") ?: ""
                val storageRe = document.getString("storageRe") ?: ""
                val newsUrl = document.getString("newsUrl") ?: ""

                Game(image, title, genre, description, cpuMi, vgaMi, ramMi, storageMi, cpuRe, vgaRe, ramRe, storageRe, newsUrl)
            }

            // Set item click listener untuk setiap item game
            adapter.setOnItemClickListener(object : GameAdapter.OnItemClickListener {
                override fun onItemClick(game: Game) {
                    val intent = Intent(requireContext(), DetailGameActivity::class.java).apply {
                        putExtra("IMAGE", game.image)
                        putExtra("TITLE", game.title)
                        putExtra("DESCRIPTION", game.description)
                        putExtra("GENRE", game.genre)
                        putExtra("CPU_MI", game.cpuMi)
                        putExtra("VGA_MI", game.vgaMi)
                        putExtra("RAM_MI", game.ramMi)
                        putExtra("STORAGE_MI", game.storageMi)
                        putExtra("CPU_RE", game.cpuRe)
                        putExtra("VGA_RE", game.vgaRe)
                        putExtra("RAM_RE", game.ramRe)
                        putExtra("STORAGE_RE", game.storageRe)
                        putExtra("NEWS_URL", game.newsUrl)
                    }
                    startActivity(intent)
                }
            })

            adapter.games = games
            adapter.notifyDataSetChanged()
        }
    }
}
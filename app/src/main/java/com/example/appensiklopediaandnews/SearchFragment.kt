package com.example.appensiklopediaandnews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appensiklopediaandnews.adapter.GameAdapter
import com.example.appensiklopediaandnews.adapter.GameAdapterSrc
import com.example.appensiklopediaandnews.model.Game
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class SearchFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var gameAdapter: GameAdapterSrc
    private lateinit var firestore: FirebaseFirestore
    private var gameList: MutableList<Game> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        searchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        firestore = Firebase.firestore

        // Inisialisasi gameAdapter
        gameAdapter = GameAdapterSrc(gameList)
        recyclerView.adapter = gameAdapter

        // Mengambil data dari Firestore
        fetchGamesFromFirestore()

        setupSearch()

        return view
    }

    private fun fetchGamesFromFirestore() {
        firestore.collection("games")
            .get()
            .addOnSuccessListener { result ->
                gameList.clear()  // Bersihkan list sebelum menambah data baru
                for (document in result) {
                    val game = document.toObject(Game::class.java)
                    gameList.add(game)
                }
                gameAdapter.notifyDataSetChanged()  // Perbarui adapter setelah data diambil
            }
            .addOnFailureListener { exception ->
                Log.w("FirestoreError", "Error getting documents: ", exception)
            }
    }

    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = if (!newText.isNullOrEmpty()) {
                    gameList.filter { it.title.contains(newText, ignoreCase = true) }
                } else {
                    gameList
                }
                gameAdapter.updateList(filteredList)
                return true
            }
        })
    }
}
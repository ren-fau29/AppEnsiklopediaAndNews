package com.example.appensiklopediaandnews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appensiklopediaandnews.R
import com.example.appensiklopediaandnews.model.Game
import com.squareup.picasso.Picasso

class GameAdapterSrc(private var gameList: List<Game>) : RecyclerView.Adapter<GameAdapterSrc.GameViewHolder>() {

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Deklarasikan elemen UI di sini
        val titleTextView: TextView = itemView.findViewById(R.id.gameTitle)
        val genreTextView: TextView = itemView.findViewById(R.id.gameGenre)
        val imageImageView: ImageView = itemView.findViewById(R.id.gameImage)

        fun bind(game: Game) {
            titleTextView.text = game.title
            genreTextView.text = game.genre

            // Menggunakan Picasso untuk memuat gambar ke imageImageView
            Picasso.get()
                .load(game.image) // pastikan game memiliki properti imageUrl
                .placeholder(R.drawable.placeholder_image) // gambar sementara
                .error(R.drawable.error_image) // gambar error jika gagal
                .into(imageImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(gameList[position])

    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    fun updateList(newList: List<Game>) {
        gameList = newList
        notifyDataSetChanged()
    }
}
package com.example.appensiklopediaandnews.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appensiklopediaandnews.DetailGameActivity
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

            // Set OnClickListener untuk membuka DetailGameActivity
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, DetailGameActivity::class.java)

                // Kirim data game ke DetailGameActivity
                intent.putExtra("IMAGE", game.image)
                intent.putExtra("TITLE", game.title)
                intent.putExtra("DESCRIPTION", game.description)
                intent.putExtra("GENRE", game.genre)
                intent.putExtra("CPU_MI", game.cpuMi)
                intent.putExtra("VGA_MI", game.vgaMi)
                intent.putExtra("RAM_MI", game.ramMi)
                intent.putExtra("STORAGE_MI", game.storageMi)
                intent.putExtra("CPU_RE", game.cpuRe)
                intent.putExtra("VGA_RE", game.vgaRe)
                intent.putExtra("RAM_RE", game.ramRe)
                intent.putExtra("STORAGE_RE", game.storageRe)
                intent.putExtra("NEWS_URL", game.newsUrl)

                // Mulai DetailGameActivity
                context.startActivity(intent)
            }
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

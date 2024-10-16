package com.example.appensiklopediaandnews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appensiklopediaandnews.R
import com.example.appensiklopediaandnews.model.Game
import com.google.firebase.firestore.DocumentSnapshot
import com.squareup.picasso.Picasso

class GameAdapter(var games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games[position]
        holder.gameTitle.text = game.title
        holder.gameGenre.text = game.genre

        // Tambahkan placeholder dan error handling pada Picasso
        Picasso.get()
            .load(game.image)
            .placeholder(R.drawable.placeholder_image) // Ganti dengan placeholder Anda
            .error(R.drawable.error_image) // Ganti dengan gambar error Anda
            .into(holder.gameImage)

        // Menambahkan klik listener untuk item, jika listener sudah di-set
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(game)
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameImage: ImageView = itemView.findViewById(R.id.gameImage)
        val gameTitle: TextView = itemView.findViewById(R.id.gameTitle)
        val gameGenre: TextView = itemView.findViewById(R.id.gameGenre)
    }

    // Interface untuk click listener
    interface OnItemClickListener {
        fun onItemClick(game: Game)
    }

    // Method untuk set listener dari luar adapter
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }
}
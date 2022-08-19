package com.egementt.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.egementt.movieapp.R
import com.egementt.movieapp.data.model.Movie
import com.google.android.material.imageview.ShapeableImageView

class SearchMoviesRWAdapter(private val list: List<Movie>, private val onClick: (Movie?) -> Unit) : RecyclerView.Adapter<SearchMoviesRWAdapter.CustomViewHolder>() {



    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        private val moviePoster = itemView.findViewById<ShapeableImageView>(R.id.iv_search_item)!!
        private val searchItemTitle = itemView.findViewById<TextView>(R.id.tv_search_item_title)
        private val languageText = itemView.findViewById<TextView>(R.id.tv_language)
        private val voteText = itemView.findViewById<TextView>(R.id.tv_popularity)

        fun bindItems(movie: Movie, onClick: (Movie?) -> Unit){
            searchItemTitle.text = movie.title
            languageText.text = itemView.context.getString(R.string.language, movie.original_language)
            voteText.text = itemView.context.getString(R.string.vote_text, movie.vote_average.toString())
            Glide.with(itemView).load(movie.poster_path).fitCenter().into(moviePoster)
            itemView.setOnClickListener {
                onClick(movie)
            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rw_search_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentMovie = list[position]
        holder.bindItems(currentMovie, onClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
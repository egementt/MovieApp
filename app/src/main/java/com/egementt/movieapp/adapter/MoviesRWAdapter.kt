package com.egementt.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginEnd
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.egementt.movieapp.R
import com.egementt.movieapp.data.model.Movie
import com.egementt.movieapp.presentation.OnItemClickListener
import com.google.android.material.imageview.ShapeableImageView

class PopularMoviesRWAdapter(private val list: List<Movie>, private val onClick: (Movie?) -> Unit) : RecyclerView.Adapter<PopularMoviesRWAdapter.CustomViewHolder>() {



    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val moviePoster = itemView.findViewById<ShapeableImageView>(R.id.imageView)!!


        fun bindItems(movie: Movie, onClick: (Movie?) -> Unit){
            Glide.with(itemView).load(movie.poster_path).centerInside().into(moviePoster)
            itemView.setOnClickListener {
                onClick(movie)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rw_movie_item, parent, false)
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
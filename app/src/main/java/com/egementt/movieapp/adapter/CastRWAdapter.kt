package com.egementt.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.egementt.movieapp.R
import com.egementt.movieapp.data.model.Cast
import com.egementt.movieapp.data.model.Movie
import com.google.android.material.imageview.ShapeableImageView

class CastRWAdapater(private val list: List<Cast>) : RecyclerView.Adapter<CastRWAdapater.CustomViewHolder>() {



    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        private val personName = itemView.findViewById<TextView>(R.id.tv_person_name)!!
        private val personImg = itemView.findViewById<ImageView>(R.id.iv_person)


        fun bindItems(cast: Cast){
            Glide.with(itemView).load(cast.profile_path).centerInside().into(personImg)
            personName.text = cast.name
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rw_cast_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentMovie = list[position]
        holder.bindItems(currentMovie)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
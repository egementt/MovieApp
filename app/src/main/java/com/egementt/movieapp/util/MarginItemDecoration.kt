package com.egementt.movieapp.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val spaceSize: Int) : RecyclerView.ItemDecoration(){

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
       with(outRect){
           left = if (parent.getChildAdapterPosition(view) == 0){
               spaceSize+spaceSize
           } else spaceSize
           right = spaceSize
       }
    }
}
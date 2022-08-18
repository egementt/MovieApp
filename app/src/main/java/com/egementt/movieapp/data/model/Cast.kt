package com.egementt.movieapp.data.model

data class Cast(
    val adult: Boolean,
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val order: Int,
    val original_name: String,
    val popularity: Double,
    var profile_path: String
){
    fun createFullImageUrl(): String {
        return "https://www.themoviedb.org/t/p/w276_and_h350_face/$profile_path"
    }
}
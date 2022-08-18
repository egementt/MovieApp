package com.egementt.movieapp.data.model

data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    var poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
){
    fun getFullImageURL( path: String = poster_path , resolution: Resolution = Resolution.LOW): String {

        return when(resolution){
            Resolution.HIGH -> "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/$poster_path"
            Resolution.MEDIUM -> "https://www.themoviedb.org/t/p/w553_and_h300_bestv2$poster_path"
            Resolution.LOW -> "https://www.themoviedb.org/t/p/w300_and_h450_bestv2$poster_path"
        }
    }

    enum class Resolution{
        LOW, MEDIUM, HIGH
    }


}
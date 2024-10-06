package com.example.surosowancyber.implement.data.network

import com.example.surosowancyber.implement.data.model.Casts
import com.example.surosowancyber.implement.data.model.Genres
import com.example.surosowancyber.implement.data.model.Movies
import com.example.surosowancyber.implement.data.model.MoviesDetail
import com.example.surosowancyber.implement.data.model.Videos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("genre/movie/list?language=en")
    fun getGenre(): Call<Genres>

    @GET("discover/movie?language=en-US")
    fun getMoviesByGenre(
        @Query("page") page: Int,
        @Query("with_genres") withGenre: String
    ): Call<Movies>

    @GET("movie/now_playing?language=en-US")
    fun getNowPlaying(
        @Query("page") page: Int
    ): Call<Movies>

    @GET("movie/{movieId}?language=en-US")
    fun getMoviesDetail(
        @Path("movieId") movieId: Int
    ): Call<MoviesDetail>

    @GET("movie/{movieId}/videos?language=en-US")
    fun getVideo(
        @Path("movieId") movieId: Int
    ): Call<Videos>

    @GET("movie/{movieId}/credits?language=en-US")
    fun getCast(
        @Path("movieId") movieId: Int
    ): Call<Casts>
}
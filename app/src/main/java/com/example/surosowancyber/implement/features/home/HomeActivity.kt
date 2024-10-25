package com.example.surosowancyber.implement.features.home

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surosowancyber.R
import com.example.surosowancyber.implement.data.model.Genres
import com.example.surosowancyber.implement.data.model.Movies
import com.example.surosowancyber.implement.data.network.ApiConfig
import com.example.surosowancyber.implement.features.home.adapter.GenreAdapter
import com.example.surosowancyber.implement.features.home.adapter.NowPlayingAdapter
import com.example.surosowancyber.implement.features.home.adapter.RecommendedAdapter
import com.example.surosowancyber.implement.utils.CenterLayoutManager
import com.example.surosowancyber.implement.utils.CenterSnapHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    private lateinit var adapterGenre: GenreAdapter
    private lateinit var adapterRecommended: RecommendedAdapter
    private lateinit var adapterNowPlaying: NowPlayingAdapter
    private lateinit var rvGenre: RecyclerView
    private lateinit var rvRecommended: RecyclerView
    private lateinit var rvNowPlaying: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        rvGenre = findViewById(R.id.rv_genre)
        rvRecommended = findViewById(R.id.rv_recommended)
        rvNowPlaying = findViewById(R.id.rv_now_playing)

        setupToolbar()
        initAdapterGenre()
        initAdapterRecommended()
        initAdapterNowPlaying()
        setDataGenre()
        setDataNowPlaying()
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.custom_toolbar)
        setSupportActionBar(toolbar)

        val toolbarTitle: TextView = findViewById(R.id.tv_toolbar)
        toolbarTitle.text = "Hey, Rizki Maul"

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    private fun initAdapterGenre() {
        adapterGenre = GenreAdapter()
        rvGenre.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvGenre.setHasFixedSize(true)
        rvGenre.adapter = adapterGenre
        adapterGenre.onItemClick = { selectedGenre ->
            setDataRecommendMovies(selectedGenre.id.toString())
        }
    }

    private fun initAdapterRecommended() {
        adapterRecommended = RecommendedAdapter()
        rvRecommended.layoutManager = CenterLayoutManager(this)
        rvRecommended.setHasFixedSize(true)
        rvRecommended.adapter = adapterRecommended
        val snapHelper = CenterSnapHelper()
        snapHelper.attachToRecyclerView(rvRecommended)
        adapterRecommended.onItemClick = { selectedMovies ->
            Toast.makeText(this, "Goto detail: ${selectedMovies.title}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initAdapterNowPlaying() {
        adapterNowPlaying = NowPlayingAdapter()
        rvNowPlaying.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvNowPlaying.setHasFixedSize(true)
        rvNowPlaying.adapter = adapterNowPlaying
        adapterNowPlaying.onItemClick = { selectedMovies ->
            Toast.makeText(this, "Goto detail: ${selectedMovies.title}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setDataGenre() {
        val client = ApiConfig.getApiService().getGenre()
        client.enqueue(object : Callback<Genres> {
            override fun onResponse(call: Call<Genres>, response: Response<Genres>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        adapterGenre.setData(responseBody.genres)
                    }
                } else {
                    Log.e("DATA GENRE", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Genres>, t: Throwable) {
                Log.e("DATA GENRE", "onFailure: ${t.message}")
            }
        })
    }

    private fun setDataRecommendMovies(idGenre: String) {
        val client = ApiConfig.getApiService().getMoviesByGenre(1, idGenre)
        client.enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        adapterRecommended.setData(responseBody.results)
                    }
                } else {
                    Log.e("HomeActivity", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.e("HomeActivity", "onFailure: ${t.message}")
            }
        })
    }

    private fun setDataNowPlaying() {
        val client = ApiConfig.getApiService().getNowPlaying(1)
        client.enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        adapterNowPlaying.setData(responseBody.results)
                    }
                } else {
                    Log.e("HomeActivity", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.e("HomeActivity", "onFailure: ${t.message}")
            }
        })
    }
}
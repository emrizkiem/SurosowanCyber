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
import com.example.surosowancyber.implement.data.network.ApiConfig
import com.example.surosowancyber.implement.features.home.adapter.GenreAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    private lateinit var adapterGenre: GenreAdapter
    private lateinit var rvGenre: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        rvGenre = findViewById(R.id.rv_genre)

        setupToolbar()
        initAdapterGenre()
        setDataGenre()
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
            Toast.makeText(this, "Selected: ${selectedGenre.name}", Toast.LENGTH_SHORT).show()
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
}
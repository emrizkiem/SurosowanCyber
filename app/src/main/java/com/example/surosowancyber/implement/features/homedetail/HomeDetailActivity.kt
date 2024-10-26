 package com.example.surosowancyber.implement.features.homedetail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.surosowancyber.R
import com.example.surosowancyber.implement.data.model.Casts
import com.example.surosowancyber.implement.data.model.MoviesDetail
import com.example.surosowancyber.implement.data.model.Videos
import com.example.surosowancyber.implement.data.network.ApiConfig
import com.example.surosowancyber.implement.features.homedetail.adapter.CastAdapter
import com.example.surosowancyber.implement.features.homedetail.adapter.VideoAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

 class HomeDetailActivity : AppCompatActivity() {
    private lateinit var adapterVideo: VideoAdapter
    private lateinit var adapterCast: CastAdapter
    private lateinit var imgPoster: ImageView
    private lateinit var chipGenre: ChipGroup
    private lateinit var tvDate: TextView
    private lateinit var rating: RatingBar
    private lateinit var tvDescription: TextView
    private lateinit var rvVideo: RecyclerView
    private lateinit var rvCast: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_detail)

        imgPoster = findViewById(R.id.img_poster)
        chipGenre = findViewById(R.id.chip_genre)
        tvDate = findViewById(R.id.tv_date)
        rating = findViewById(R.id.rating)
        tvDescription = findViewById(R.id.tv_description)
        rvCast = findViewById(R.id.rv_cast)
        rvVideo = findViewById(R.id.rv_video)

        retrievingData()
        initAdapterCast()
        initAdapterVideo()
    }

    private fun retrievingData() {
        val data = intent.getIntExtra(MOVIE_ID, -1)
        if (data != -1) {
            setDataDetail(data)
            setDataVideo(data)
            setDataCast(data)
        } else {
            Log.e("GET DATA DETAIL", "Movie ID is null or invalid")
        }
    }

    private fun setupToolbar(movieName: String) {
        val toolbar: Toolbar = findViewById(R.id.custom_toolbar)
        setSupportActionBar(toolbar)

        val toolbarTitle: TextView = findViewById(R.id.tv_toolbar)
        toolbarTitle.text = movieName

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initAdapterVideo() {
        adapterVideo = VideoAdapter()
        rvVideo.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvVideo.setHasFixedSize(true)
        rvVideo.adapter = adapterVideo
        adapterVideo.onItemClick = { key ->

        }
    }

    private fun initAdapterCast() {
        adapterCast = CastAdapter()
        rvCast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCast.setHasFixedSize(true)
        rvCast.adapter = adapterCast
    }

    private fun setDataDetail(movieId: Int) {
        val client = ApiConfig.getApiService().getMoviesDetail(movieId)
        client.enqueue(object : Callback<MoviesDetail> {
            override fun onResponse(call: Call<MoviesDetail>, response: Response<MoviesDetail>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setupViews(responseBody)
                    }
                } else {
                    Log.e("DATA DETAIL", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MoviesDetail>, t: Throwable) {
                Log.e("DATA DETAIL", "onFailure: ${t.message}")
            }

        })
    }

    private fun setDataVideo(movieId: Int) {
        val client = ApiConfig.getApiService().getVideo(movieId)
        client.enqueue(object : Callback<Videos> {
            override fun onResponse(call: Call<Videos>, response: Response<Videos>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        adapterVideo.setData(responseBody.results)
                    }
                } else {
                    Log.e("DATA VIDEO", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Videos>, t: Throwable) {
                Log.e("DATA VIDEO", "onFailure: ${t.message}")
            }

        })
    }

    private fun setDataCast(movieId: Int) {
        val client = ApiConfig.getApiService().getCast(movieId)
        client.enqueue(object : Callback<Casts> {
            override fun onResponse(call: Call<Casts>, response: Response<Casts>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        adapterCast.setData(responseBody.cast)
                    }
                } else {
                    Log.e("DATA CAST", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Casts>, t: Throwable) {
                Log.e("DATA CAST", "onFailure: ${t.message}")
            }

        })
    }

    private fun setupViews(data: MoviesDetail) {
        setupToolbar(data.originalTitle)
        tvDate.text = data.releaseDate
        tvDescription.text = data.overview
        rating.rating = data.voteAverage.div(2)

        data.genres.forEach { genre ->
            val chip = Chip(chipGenre.context).apply {
                id = View.generateViewId()
                text = genre.name
                isCheckable = false
                isCheckedIconVisible = false
                chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.bg_chip_detail)
                shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCorners(CornerFamily.ROUNDED, 12f)
                    .build()
            }
            chipGenre.addView(chip)
        }

        Glide.with(this)
            .load(ApiConfig.IMAGE_BASE_URL + data.backdropPath)
            .into(imgPoster)
    }

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
    }
}
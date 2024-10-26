package com.example.surosowancyber.implement.features.youtube

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.surosowancyber.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class YoutubeActivity : AppCompatActivity() {
    private lateinit var youtubePlayer: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        youtubePlayer = findViewById(R.id.youtube_player)

        setupViews()
    }

    private fun setupViews() {
        val data = intent.getStringExtra(KEY_YOUTUBE)
        data.let {
            if (it != null) {
                initVideoPlayer(it)
            }
        }
    }

    private fun initVideoPlayer(key: String) {
        youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(key, 0f)
            }
        })
    }

    companion object {
        const val KEY_YOUTUBE = "KEY_YOUTUBE"
    }
}
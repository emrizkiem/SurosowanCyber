package com.example.surosowancyber.learn.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.surosowancyber.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        Log.d("OnCreate", "OnCreate Called")

        val goToDetail: Button = findViewById(R.id.goToDetail)
        goToDetail.setOnClickListener {
            // Intent Explicit
            val moveToDetail = Intent(this, DetailActivity::class.java)
            startActivity(moveToDetail)
        }

        val goToCube: Button = findViewById(R.id.goToCube)
        goToCube.setOnClickListener {
            val moveToCube = Intent(this, VolumeCubeActivity::class.java)
            startActivity(moveToCube)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("OnStart", "OnStart Called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("OnResume", "OnResume Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("OnPause", "OnPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("OnStop", "OnStop Called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("OnRestart", "OnRestart Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("OnDestroy", "OnDestroy Called")
    }
}
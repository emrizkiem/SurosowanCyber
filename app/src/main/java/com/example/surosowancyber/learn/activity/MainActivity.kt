package com.example.surosowancyber.learn.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.Manifest
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.surosowancyber.R

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var openCamera: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Logging
        Log.d("OnCreate", "OnCreate Called")

        // Navigation goToDetail
        val goToDetail: Button = findViewById(R.id.goToDetail)
        goToDetail.setOnClickListener {
            // Intent Explicit
            val moveToDetail = Intent(this, DetailActivity::class.java)
            startActivity(moveToDetail)
        }

        // Navigation goToCubeActivity
        val goToCube: Button = findViewById(R.id.goToCube)
        goToCube.setOnClickListener {
            val moveToCube = Intent(this, VolumeCubeActivity::class.java)
            startActivity(moveToCube)
        }

        // Intent Implicit
        val goToDialNumber: Button = findViewById(R.id.goToDialNumber)
        goToDialNumber.setOnClickListener {
            val phoneNumber = "081234567890"
            val dialNumberIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(dialNumberIntent)
        }

        imageView = findViewById(R.id.img_camera)
        openCamera = findViewById(R.id.goToCamera)

        openCamera.setOnClickListener {
            checkPermission()
        }
    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) -> {
                showPermissionRationale()
            }
            else -> {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
    }

    private fun showPermissionRationale() {
        AlertDialog.Builder(this)
            .setTitle("Camera Permission Needed")
            .setMessage("This app requires camera access to take photo. Please grant the permission.")
            .setPositiveButton("OK") { _, _ ->
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(cameraIntent)
    }

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    showSettingDialog()
                } else {
                    Toast.makeText(this, "Camera permission is needed to use the camera", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showSettingDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("You have denied the camera permission. Please enable it in app settings to use the camera.")
            .setPositiveButton("Go to Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
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
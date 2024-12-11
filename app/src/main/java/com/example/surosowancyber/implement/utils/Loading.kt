package com.example.surosowancyber.implement.utils

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.surosowancyber.R

object Loading {
    private lateinit var builder: AlertDialog.Builder
    private lateinit var loading: AlertDialog

    fun showLoading(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_loading, null)
        builder = AlertDialog.Builder(context)
        builder.setView(view)
        builder.setCancelable(false)
        loading = builder.create()
        loading.show()

        Resources.getSystem().displayMetrics
        val displayWidth = Resources.getSystem().displayMetrics.widthPixels
        val dialogWindowWidth = (displayWidth * 0.20f).toInt()
        loading.window?.setBackgroundDrawableResource(R.drawable.bg_white)
        loading.window?.setLayout(dialogWindowWidth, dialogWindowWidth)
    }

    fun hideLoading() {
        if (loading.isShowing) {
            loading.dismiss()
        }
    }
}
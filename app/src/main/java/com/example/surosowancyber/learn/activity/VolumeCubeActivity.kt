package com.example.surosowancyber.learn.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.surosowancyber.R

class VolumeCubeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var edtLength: EditText
    private lateinit var edtWidth: EditText
    private lateinit var edtHeight: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_volume_cube)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtLength = findViewById(R.id.et_length)
        edtWidth = findViewById(R.id.et_width)
        edtHeight = findViewById(R.id.et_height)
        btnCalculate = findViewById(R.id.btn_calculate)
        tvResult = findViewById(R.id.tv_result)
        btnCalculate.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.btn_calculate) {
            val inputLength = edtLength.text.toString().trim()
            val inputWidth = edtWidth.text.toString().trim()
            val inputHeight = edtHeight.text.toString().trim()
            var isEmptyField = false

            if (inputLength.isEmpty()) {
                isEmptyField = true
                edtLength.error = "Field ini tidak boleh kosong"
            }

            if (inputWidth.isEmpty()) {
                isEmptyField = true
                edtLength.error = "Field ini tidak boleh kosong"
            }

            if (inputHeight.isEmpty()) {
                isEmptyField = true
                edtLength.error = "Field ini tidak boleh kosong"
            }

            if (!isEmptyField) {
                val volume = inputLength.toDouble() * inputWidth.toDouble() * inputHeight.toDouble()
                tvResult.text = volume.toString()
            }
        }
    }
}
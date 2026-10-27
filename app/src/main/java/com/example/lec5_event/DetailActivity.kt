package com.example.lec5_event

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DetailActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tvChiTiet = findViewById<TextView>(R.id.tvChiTiet)
        val btnQuayLai = findViewById<Button>(R.id.btnQuayLai)

        val hoTen = intent.getStringExtra("hoTen") ?: ""
        val mssv = intent.getStringExtra("mssv") ?: ""
        val email = intent.getStringExtra("email") ?: ""
        val gioiTinh = intent.getStringExtra("gioiTinh") ?: ""
        val mangXaHoi = intent.getStringExtra("mangXaHoi") ?: ""

        val noiDung = """
            Họ và tên: $hoTen
            
            Mã số sinh viên: $mssv
            
            Email: $email
            
            Giới tính: $gioiTinh
            
            Mạng xã hội: $mangXaHoi
        """.trimIndent()

        tvChiTiet.text = noiDung

        btnQuayLai.setOnClickListener {
            finish()
        }
    }
}

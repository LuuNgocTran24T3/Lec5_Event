package com.example.lec5_event

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import android.content.Intent
import com.google.android.material.textfield.TextInputEditText

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editHoTen = findViewById<TextInputEditText>(R.id.editHoTen)
        val editMssv = findViewById<TextInputEditText>(R.id.editMssv)
        val editEmail = findViewById<TextInputEditText>(R.id.editEmail)

        val rgGioiTinh = findViewById<RadioGroup>(R.id.rgGioiTinh)
        val rbNam = findViewById<RadioButton>(R.id.rbNam)
        val rbNu = findViewById<RadioButton>(R.id.rbNu)
        val rbKhac = findViewById<RadioButton>(R.id.rbKhac)

        val cbFacebook = findViewById<CheckBox>(R.id.cbFacebook)
        val cbInstagram = findViewById<CheckBox>(R.id.cbInstagram)
        val cbTiktok = findViewById<CheckBox>(R.id.cbTiktok)
        val cbZalo = findViewById<CheckBox>(R.id.cbZalo)

        val btnDangKy = findViewById<Button>(R.id.btnDangKy)
        val tvThongTinSinhVien = findViewById<TextView>(R.id.tvThongTinSinhVien)

        // EVENT 1: EditText - bắt sự kiện nhập dữ liệu
        fun batSuKienNhap(editText: EditText, tenTruong: String) {
            var daThongBao = false

            editText.setOnFocusChangeListener { _, coFocus ->
                if (coFocus) {
                    daThongBao = false
                    Toast.makeText(
                        this,
                        "Bạn đang nhập $tenTruong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int,
                    count: Int, after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?, start: Int,
                    before: Int, count: Int
                ) {
                    if (!s.isNullOrEmpty() && !daThongBao) {
                        Toast.makeText(
                            this@MainActivity,
                            "Đã nhập $tenTruong",
                            Toast.LENGTH_SHORT
                        ).show()
                        daThongBao = true
                    }
                    if (s.isNullOrEmpty()) {
                        daThongBao = false
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

        batSuKienNhap(editHoTen, "họ tên")
        batSuKienNhap(editMssv, "MSSV")
        batSuKienNhap(editEmail, "email")

        // EVENT 2: RadioGroup - chọn giới tính
        rgGioiTinh.setOnCheckedChangeListener { _, checkedId ->
            val gioiTinh = when (checkedId) {
                R.id.rbNam -> "Nam"
                R.id.rbNu -> "Nữ"
                R.id.rbKhac -> "Khác"
                else -> ""
            }

            if (gioiTinh.isNotEmpty()) {
                Toast.makeText(
                    this,
                    "Bạn đã chọn giới tính: $gioiTinh",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // EVENT 3: CheckBox - chọn mạng xã hội
        val danhSachCheckBox = listOf(
            cbFacebook, cbInstagram, cbTiktok, cbZalo
        )

        for (checkBox in danhSachCheckBox) {
            checkBox.setOnCheckedChangeListener { button, isChecked ->
                val trangThai =
                    if (isChecked) "Đã chọn" else "Đã bỏ chọn"

                Toast.makeText(
                    this,
                    "$trangThai ${button.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // EVENT 4: Button đăng ký - chuyển màn hình
        btnDangKy.setOnClickListener {
            val hoTen = editHoTen.text.toString().trim()
            val mssv = editMssv.text.toString().trim()
            val email = editEmail.text.toString().trim()

            if (hoTen.isEmpty() || mssv.isEmpty() ||
                email.isEmpty() || rgGioiTinh.checkedRadioButtonId == -1
            ) {
                Toast.makeText(
                    this,
                    "Vui lòng nhập đủ thông tin và chọn giới tính!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val gioiTinh = when (rgGioiTinh.checkedRadioButtonId) {
                R.id.rbNam -> "Nam"
                R.id.rbNu -> "Nữ"
                R.id.rbKhac -> "Khác"
                else -> ""
            }

            val mangXaHoi = danhSachCheckBox
                .filter { it.isChecked }
                .joinToString(", ") { it.text.toString() }
                .ifEmpty { "Chưa chọn" }

            // Cập nhật giao diện bên dưới cho tvThongTinSinhVien
            tvThongTinSinhVien.text = "Họ tên: $hoTen\nMSSV: $mssv\nEmail: $email\nGiới tính: $gioiTinh\nMạng xã hội: $mangXaHoi"

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("hoTen", hoTen)
            intent.putExtra("mssv", mssv)
            intent.putExtra("email", email)
            intent.putExtra("gioiTinh", gioiTinh)
            intent.putExtra("mangXaHoi", mangXaHoi)

            startActivity(intent)
        }
    }
}

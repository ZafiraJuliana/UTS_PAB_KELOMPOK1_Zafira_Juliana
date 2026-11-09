package com.example.uts_catatanharian.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uts_catatanharian.R
import com.example.uts_catatanharian.data.database.AppDatabase_127
import com.example.uts_catatanharian.data.entity.UserEntity_127
import com.example.uts_catatanharian.data.repository.UserRepository_127
import com.example.uts_catatanharian.utils.HashUtils_127
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity_127 : AppCompatActivity() {

    private lateinit var userRepository: UserRepository_127

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val userDao = AppDatabase_127.getDatabase_127(this).userDao_127()
        userRepository = UserRepository_127(userDao)

        val etUsername = findViewById<TextInputEditText>(R.id.et_reg_username_127)
        val etPassword = findViewById<TextInputEditText>(R.id.et_reg_password_127)
        val etConfirmPassword = findViewById<TextInputEditText>(R.id.et_reg_confirm_password_127)
        val btnRegister = findViewById<MaterialButton>(R.id.btn_register_127)
        val tvToLogin = findViewById<android.widget.TextView>(R.id.tv_to_login_127)

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Password dan konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                val existingUser = userRepository.getUserByUsername_127(username)
                if (existingUser != null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity_127, "Username sudah digunakan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val salt = HashUtils_127.generateSalt()
                    val passwordHash = HashUtils_127.hashPassword(password, salt)
                    val newUser = UserEntity_127(username = username, passwordHash = passwordHash, salt = salt)
                    userRepository.registerUser_127(newUser)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity_127, "Registrasi Berhasil. Silakan Login", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }

        tvToLogin.setOnClickListener {
            finish()
        }
    }
}

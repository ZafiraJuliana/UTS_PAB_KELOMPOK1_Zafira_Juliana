package com.example.uts_catatanharian.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uts_catatanharian.R
import com.example.uts_catatanharian.data.database.AppDatabase_127
import com.example.uts_catatanharian.data.repository.UserRepository_127
import com.example.uts_catatanharian.ui.dashboard.DashboardActivity_127
import com.example.uts_catatanharian.utils.HashUtils_127
import com.example.uts_catatanharian.utils.SessionManager_127
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity_127 : AppCompatActivity() {

    private lateinit var userRepository: UserRepository_127
    private lateinit var sessionManager: SessionManager_127

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userDao = AppDatabase_127.getDatabase_127(this).userDao_127()
        userRepository = UserRepository_127(userDao)
        sessionManager = SessionManager_127(this)

        val etUsername = findViewById<TextInputEditText>(R.id.et_username_127)
        val etPassword = findViewById<TextInputEditText>(R.id.et_password_127)
        val btnLogin = findViewById<MaterialButton>(R.id.btn_login_127)
        val tvToRegister = findViewById<android.widget.TextView>(R.id.tv_to_register_127)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                val user = userRepository.getUserByUsername_127(username)
                withContext(Dispatchers.Main) {
                    if (user != null && HashUtils_127.verifyPassword(password, user.salt, user.passwordHash)) {
                        sessionManager.createLoginSession(user.id, user.username)
                        Toast.makeText(this@LoginActivity_127, "Login Berhasil", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity_127, DashboardActivity_127::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity_127, "Username atau password salah", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        tvToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity_127::class.java)
            startActivity(intent)
        }
    }
}

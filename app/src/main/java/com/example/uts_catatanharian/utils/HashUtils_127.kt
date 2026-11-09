package com.example.uts_catatanharian.utils

import java.security.MessageDigest
import java.security.SecureRandom
import android.util.Base64

object HashUtils_127 {
    fun generateSalt(): String {
        val random = SecureRandom()
        val saltBytes = ByteArray(16)
        random.nextBytes(saltBytes)
        return Base64.encodeToString(saltBytes, Base64.NO_WRAP)
    }

    fun hashPassword(password: String, salt: String): String {
        val combined = password + salt
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(combined.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(hashBytes, Base64.NO_WRAP)
    }

    fun verifyPassword(password: String, salt: String, expectedHash: String): Boolean {
        val actualHash = hashPassword(password, salt)
        return actualHash == expectedHash
    }
}

package com.example.appensiklopediaandnews

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signupRedirectText = findViewById<TextView>(R.id.loginRedirectText)
        // Set click listener for signupRedirectText
        signupRedirectText.setOnClickListener {
            // Berpindah ke SignUpActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
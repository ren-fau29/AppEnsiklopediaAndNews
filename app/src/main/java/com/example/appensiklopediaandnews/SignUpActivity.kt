package com.example.appensiklopediaandnews

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

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

        // Inisialisasi Firebase Auth dan Firestore
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Referensi ke view
        val nameEditText: EditText = findViewById(R.id.signup_name)
        val emailEditText: EditText = findViewById(R.id.signup_email)
        val usernameEditText: EditText = findViewById(R.id.signup_username)
        val passwordEditText: EditText = findViewById(R.id.signup_password)
        val signupButton: Button = findViewById(R.id.signup_button)
        val loginRedirectText: TextView = findViewById(R.id.loginRedirectText)

        // Handle signup button click
        signupButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proses Sign Up
            signUpUser(name, email, username, password)
        }

        // Redirect to Login activity
        loginRedirectText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun signUpUser(name: String, email: String, username: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User berhasil terdaftar, simpan data ke Firestore
                    val userId = auth.currentUser?.uid
                    val user = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "username" to username
                    )

                    if (userId != null) {
                        firestore.collection("users").document(userId).set(user)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Sign Up successful", Toast.LENGTH_SHORT).show()
                                // Redirect ke activity berikutnya atau dashboard
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    // Gagal sign up
                    Toast.makeText(this, "Sign Up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
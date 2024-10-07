package com.example.appensiklopediaandnews

import android.content.Context
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
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Cek status login
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            // Jika sudah login, langsung pindah ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Bind views
        usernameInput = findViewById(R.id.login_username)
        passwordInput = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_button)

        val signupRedirectText = findViewById<TextView>(R.id.signupRedirectText)
        // Set click listener for signupRedirectText
        signupRedirectText.setOnClickListener {
            // Berpindah ke SignUpActivity
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }


        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginUser(username, password)
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser (username: String, password: String) {
        val usersCollection = firestore.collection("users")
        usersCollection.whereEqualTo("username", username).get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userDoc = documents.first()
                    val storedPassword = userDoc.getString("password")
                    if (storedPassword == password) {
                        // Simpan status login di SharedPreferences
                        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("isLoggedIn", true)
                        editor.putString("name", userDoc.getString("name"))
                        editor.putString("email", userDoc.getString("email"))
                        editor.putString("username", userDoc.getString("username"))
                        editor.putString("password", userDoc.getString("password"))
                        editor.apply()

                        // Login success, move to MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("name", userDoc.getString("name"))
                        intent.putExtra("email", userDoc.getString("email"))
                        intent.putExtra("username", userDoc.getString("username"))
                        intent.putExtra("password", userDoc.getString("password"))
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "User  not found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
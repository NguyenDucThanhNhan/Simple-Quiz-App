package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.os.postDelayed
import com.example.quizapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var binding: ActivityLoginBinding
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edtEmail.editText?.setText(intent.getStringExtra("EMAIL"))
        binding.edtPassword.editText?.setText(intent.getStringExtra("PASSWORD"))

        binding.btnSignIn.setOnClickListener {
            val email = binding.edtEmail.editText?.text.toString()
            val password = binding.edtPassword.editText?.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Firebase.auth.signInWithEmailAndPassword(email, password)
             .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, QuizActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
             }
        }

        binding.txtSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
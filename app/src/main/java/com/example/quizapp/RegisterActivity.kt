package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.os.postDelayed
import com.example.quizapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var binding: ActivityRegisterBinding
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            Firebase.auth.createUserWithEmailAndPassword(
                binding.edtEmail.editText?.text.toString(),
                binding.edtPassword.editText?.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "User created!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "User not created!", Toast.LENGTH_SHORT).show()
                }
            }

            Handler(Looper.getMainLooper()).postDelayed(1000) {
                intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("EMAIL", binding.edtEmail.editText?.text.toString())
                intent.putExtra("PASSWORD", binding.edtPassword.editText?.text.toString())
                startActivity(intent)
            }
        }
    }
}
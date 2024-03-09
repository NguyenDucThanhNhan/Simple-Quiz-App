package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizapp.databinding.ActivityScoreBinding

private lateinit var binding: ActivityScoreBinding
class ScoreActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtScore.setText("Chúc mừng bạn đã đạt được ${intent.getIntExtra("SCORE", 0)} điểm!")
        binding.btnAgain.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
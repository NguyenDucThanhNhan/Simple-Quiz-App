package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.core.os.postDelayed
import com.example.quizapp.databinding.ActivityQuizBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Collections

private lateinit var binding: ActivityQuizBinding
class QuizActivity : AppCompatActivity() {
    private lateinit var list: ArrayList<QuestionModel>
    private var count: Int = 0
    private var score: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.linScore.visibility = View.GONE
        //tao list
        list = ArrayList<QuestionModel>()
        Firebase.firestore.collection("quiz")
            .get().addOnSuccessListener {doct ->
                list.clear()
                for (i in doct.documents){
                    var questionModel = i.toObject(QuestionModel::class.java)
                    list.add(questionModel!!)
                }

                //xao tron list
                list.shuffle()

                //tao cau hoi dau tien
                binding.txtQuestion.setText(list[0].question)
                binding.btnOption1.text = list[0].option1
                binding.btnOption2.text = list[0].option2
                binding.btnOption3.text = list[0].option3
                binding.btnOption4.text = list[0].option4
            }

        binding.btnOption1.setOnClickListener {
            blink(1)
            disableAllButton()
            Handler(Looper.getMainLooper()).postDelayed(2500) {
                nextData(binding.btnOption1.text.toString(), 1)
            }
        }
        binding.btnOption2.setOnClickListener {
            blink(2)
            disableAllButton()
            Handler(Looper.getMainLooper()).postDelayed(2500) {
                nextData(binding.btnOption2.text.toString(), 2)
            }
        }
        binding.btnOption3.setOnClickListener {
            blink(3)
            disableAllButton()
            Handler(Looper.getMainLooper()).postDelayed(2500) {
                nextData(binding.btnOption3.text.toString(), 3)
            }
        }
        binding.btnOption4.setOnClickListener {
            blink(4)
            disableAllButton()
            Handler(Looper.getMainLooper()).postDelayed(2500) {
                nextData(binding.btnOption4.text.toString(), 4)
            }
        }
    }

    private fun disableAllButton() {
        binding.btnOption1.isEnabled = false
        binding.btnOption2.isEnabled = false
        binding.btnOption3.isEnabled = false
        binding.btnOption4.isEnabled = false
    }

    private fun enableAllButton() {
        binding.btnOption1.isEnabled = true
        binding.btnOption2.isEnabled = true
        binding.btnOption3.isEnabled = true
        binding.btnOption4.isEnabled = true
    }

    private fun nextData(i: String, d: Int) {
        chooseAnswer(i, d)
        count++
        Handler(Looper.getMainLooper()).postDelayed(3000) {
            if (count >= list.size) {
                val intent = Intent(this, ScoreActivity::class.java)
                intent.putExtra("SCORE", score)
                startActivity(intent)
                finish()
            } else {
                binding.txtQuestion.setText(list[count].question)
                binding.btnOption1.text = list[count].option1
                binding.btnOption2.text = list[count].option2
                binding.btnOption3.text = list[count].option3
                binding.btnOption4.text = list[count].option4
                enableAllButton()
                reset()
            }
        }
    }

    private fun reset() {
        binding.btnOption1.setBackgroundResource(R.drawable.layout_normal)
        binding.btnOption2.setBackgroundResource(R.drawable.layout_normal)
        binding.btnOption3.setBackgroundResource(R.drawable.layout_normal)
        binding.btnOption4.setBackgroundResource(R.drawable.layout_normal)
        binding.linScore.visibility = View.GONE
    }

    private fun chooseAnswer(i: String, d: Int) {
        if (count < list.size) {

                //corect answer
                if (list[count].answer.equals(i)){
                    when (d) {
                        1 -> binding.btnOption1.setBackgroundResource(R.drawable.layout_correct)
                        2 -> binding.btnOption2.setBackgroundResource(R.drawable.layout_correct)
                        3 -> binding.btnOption3.setBackgroundResource(R.drawable.layout_correct)
                        4 -> binding.btnOption4.setBackgroundResource(R.drawable.layout_correct)
                    }

                //increase score
                Handler(Looper.getMainLooper()).postDelayed(1000) {
                    binding.linScore.visibility = View.VISIBLE
                }
                score++
            } else {
                //wrong answer
                when (d) {
                    1 -> binding.btnOption1.setBackgroundResource(R.drawable.layout_wrong)
                    2 -> binding.btnOption2.setBackgroundResource(R.drawable.layout_wrong)
                    3 -> binding.btnOption3.setBackgroundResource(R.drawable.layout_wrong)
                    4 -> binding.btnOption4.setBackgroundResource(R.drawable.layout_wrong)
                }

                when (list[count].answer) {
                    binding.btnOption1.text.toString() -> binding.btnOption1.setBackgroundResource(R.drawable.layout_correct)
                    binding.btnOption2.text.toString() -> binding.btnOption2.setBackgroundResource(R.drawable.layout_correct)
                    binding.btnOption3.text.toString() -> binding.btnOption3.setBackgroundResource(R.drawable.layout_correct)
                    binding.btnOption4.text.toString() -> binding.btnOption4.setBackgroundResource(R.drawable.layout_correct)
                }

                Handler(Looper.getMainLooper()).postDelayed(3000) {
                    val intent = Intent(this, ScoreActivity::class.java)
                    intent.putExtra("SCORE", score)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun blink(d: Int) {
        val delay = 500L

        Handler(Looper.getMainLooper()).apply {
            when (d) {
                1 -> binding.btnOption1.setBackgroundResource(R.drawable.layout_wait)
                2 -> binding.btnOption2.setBackgroundResource(R.drawable.layout_wait)
                3 -> binding.btnOption3.setBackgroundResource(R.drawable.layout_wait)
                4 -> binding.btnOption4.setBackgroundResource(R.drawable.layout_wait)
            }

            postDelayed({
                when (d) {
                    1 -> binding.btnOption1.setBackgroundResource(R.drawable.layout_normal)
                    2 -> binding.btnOption2.setBackgroundResource(R.drawable.layout_normal)
                    3 -> binding.btnOption3.setBackgroundResource(R.drawable.layout_normal)
                    4 -> binding.btnOption4.setBackgroundResource(R.drawable.layout_normal)
                }
            }, delay)

            postDelayed({
                when (d) {
                    1 -> binding.btnOption1.setBackgroundResource(R.drawable.layout_wait)
                    2 -> binding.btnOption2.setBackgroundResource(R.drawable.layout_wait)
                    3 -> binding.btnOption3.setBackgroundResource(R.drawable.layout_wait)
                    4 -> binding.btnOption4.setBackgroundResource(R.drawable.layout_wait)
                }
            }, delay * 2)

            postDelayed({
                when (d) {
                    1 -> binding.btnOption1.setBackgroundResource(R.drawable.layout_normal)
                    2 -> binding.btnOption2.setBackgroundResource(R.drawable.layout_normal)
                    3 -> binding.btnOption3.setBackgroundResource(R.drawable.layout_normal)
                    4 -> binding.btnOption4.setBackgroundResource(R.drawable.layout_normal)
                }
            }, delay * 3)
        }
    }
}
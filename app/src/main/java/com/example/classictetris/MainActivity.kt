package com.example.classictetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.classictetris.databinding.ActivityMainBinding
import com.example.classictetris.storage.AppPreferences
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnNewGame.setOnClickListener(this::onBtnNewGameClick)
        binding.btnResetScore.setOnClickListener(this::onBtnResetScoreClick)
        binding.btnExit.setOnClickListener(this::onBtnExitClick)
    }

    private fun onBtnNewGameClick(view: View){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    private fun onBtnResetScoreClick(view: View){
        val preferences = AppPreferences(this)
        preferences.clearHighScore()
        Snackbar.make(view, "Score successfully reset", Snackbar.LENGTH_SHORT).show()
        setTvHighScoreText(preferences.getHighScore())
    }
    private fun onBtnExitClick(view: View){
        System.exit(0)
    }

    private fun setTvHighScoreText(score: Int){
        val text = "High score: $score"
        binding.tvHighScore.text = text
    }

}
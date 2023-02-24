package com.example.classictetris

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.classictetris.databinding.ActivityGameBinding
import com.example.classictetris.storage.AppPreferences

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var appPreferences: AppPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appPreferences = AppPreferences(this)

        updateHighScore()
        updateCurrentScore()
    }

    private fun updateHighScore(){
        binding.tvHighScore.text = "${appPreferences.getHighScore()}"
    }
    private fun updateCurrentScore(){
        binding.tvHighScore.text="0"
    }
}
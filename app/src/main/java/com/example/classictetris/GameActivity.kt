package com.example.classictetris

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.example.classictetris.constants.Motions
import com.example.classictetris.databinding.ActivityGameBinding
import com.example.classictetris.models.AppModel
import com.example.classictetris.storage.AppPreferences
import com.example.classictetris.view.TetrisView

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    var tvHighScore: TextView? = null
    var tvCurrentScore: TextView? = null

    private lateinit var tetrisView: TetrisView
    var appPreferences: AppPreferences? = null
    private val appModel:AppModel = AppModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appPreferences = AppPreferences(this)
        appModel.setPreferences(appPreferences)

        val btnRestart = binding.btnRestart
        tvHighScore = binding.tvHighScore
        tvCurrentScore = binding.tvCurrentScore
        tetrisView = binding.viewTetris
        tetrisView.setActivity(this)
        tetrisView.setModel(appModel)
        tetrisView.setOnTouchListener(this::onTetrisViewTouch)
        btnRestart.setOnClickListener(this::btnRestartClick)

        updateHighScore()
        updateCurrentScore()
    }

    private fun updateHighScore(){
        binding.tvHighScore.text = "${appPreferences?.getHighScore()}"
    }
    private fun updateCurrentScore(){
        binding.tvCurrentScore.text="0"
    }

    private fun btnRestartClick(view: View){
        appModel.restertGame()
    }

    private fun onTetrisViewTouch(view:View, event:MotionEvent):Boolean{
        if(appModel.isGameOver() || appModel.isGameAwaiting()){
            appModel.startGame()
            tetrisView.setGameCommandWithDelay(Motions.DOWN)
        }else if(appModel.isGameActive()){
            when(resolveTouchDirection(view, event)){
                0 -> moveTetromino(Motions.LEFT)
                1 -> moveTetromino(Motions.ROTATE)
                2 -> moveTetromino(Motions.DOWN)
                3 -> moveTetromino(Motions.RIGHT)
            }
        }
        return true
    }

    private fun resolveTouchDirection(view:View, event: MotionEvent):Int{
        val x = event.x/view.width
        val y = event.y/view.height
        val direction:Int
        direction = if(y >x){
            if(x>1 - y) 2 else 0
        }else{
            if (x > 1-y) 3 else 0
        }
        return direction
    }

    private fun moveTetromino(motions: Motions){
        if(appModel.isGameActive())
            tetrisView.setGameCommand(motions)
    }
}
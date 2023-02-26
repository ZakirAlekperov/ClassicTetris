package com.example.classictetris.view

import android.os.Message
import android.widget.Toast
import com.example.classictetris.constants.Motions
import com.google.android.material.snackbar.Snackbar
import java.util.logging.Handler

private class ViewHandler(private val owner:TetrisView):Handler() {
    override fun handleMessage(message: Message){
        if(message.what ==0){
            if(owner.model != null){
                if (owner.model!!.isGameOver()){
                    owner.model?.endGame()
                    Toast.makeText(owner.activity, "Game over", Toast.LENGTH_LONG).show()
                }
                if(owner.model!!.isGameActive()){
                    owner.setGameCommandWithDelay(Motions.DOWN)
                }
            }
        }
    }

    fun sleep(delay:Long){
        this.removeMessage(0)
        sendMessageDelayed(obtainMessage(0), delay)
    }
}
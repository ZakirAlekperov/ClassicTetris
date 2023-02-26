package com.example.classictetris.models

import android.graphics.Point
import com.example.classictetris.constants.CellConstants
import com.example.classictetris.constants.FieldConstants
import com.example.classictetris.constants.Statuses
import com.example.classictetris.helper.HelperFunctions
import com.example.classictetris.storage.AppPreferences
import java.text.FieldPosition

class AppModel {
    var score = 0
    private var preferences:AppPreferences? = null

    var currentBlock:Block? = null
    var currentState:String = Statuses.AWAITING_START.name
    var helperFunctions: HelperFunctions = HelperFunctions()

    private var field:Array<ByteArray> = helperFunctions.array2dofByte(
        FieldConstants.ROW_COUNT.value,
        FieldConstants.COLUMN_COUNT.value
    )

    fun setPreferences(preferences: AppPreferences?){
        this.preferences = preferences
    }

    fun getCellStatus(row: Int, column:Int):Byte?{
        return field[row][column]
    }
    private fun setCellStatus(row:Int, column:Int, status:Byte?){
        if(status != null){
            field[row][column] = status
        }
    }
    //TODO Подумать, как выделить сущность.
    fun isGameOver():Boolean{
        return currentState == Statuses.OVER.name
    }
    fun isGameActive():Boolean{
        return currentState == Statuses.ACTIVE.name
    }

    fun isGameAwaiting():Boolean{
        return currentState == Statuses.AWAITING_START.name
    }

    private fun boostScore(){
        score +=10
        if(score > preferences?.getHighScore() as Int)
            preferences?.saveHighScore(score)
    }

    private fun generateNextBlock(){
        currentBlock = Block.createBlock()
    }

    private fun validTranslation(position: Point, shape: Array<ByteArray>):Boolean{
        return if (position.y < 0 || position.x < 0)
            false
        else if (position.y + shape.size > FieldConstants.ROW_COUNT.value)
            false
        else if (position.x + shape[0].size > FieldConstants.COLUMN_COUNT.value)
            false
        else
            isEmptyPosition(position, shape)
    }

    private fun isEmptyPosition(position: Point, shape: Array<ByteArray>):Boolean{
        for(i in shape.indices){
            for(j in shape[i].indices){
                val y = position.y + i
                val x = position.x + j
                if(CellConstants.EMPTY.value != shape[i][j] && CellConstants.EMPTY.value != field[y][x]){
                    return false
                }
            }
        }
        return true
    }

    private fun moveValid(position: Point, frameNumber: Int?):Boolean{
        val shape:Array<ByteArray>? = currentBlock?.getShape(frameNumber as Int)
        return validTranslation(position,shape as Array<ByteArray>)
    }



}
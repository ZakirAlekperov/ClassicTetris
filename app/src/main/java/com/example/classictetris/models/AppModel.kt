package com.example.classictetris.models

import com.example.classictetris.constants.FieldConstants
import com.example.classictetris.helper.HelperFunctions
import com.example.classictetris.storage.AppPreferences

class AppModel {
    val score = 0
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
}
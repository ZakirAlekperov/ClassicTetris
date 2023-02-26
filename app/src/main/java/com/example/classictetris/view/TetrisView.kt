package com.example.classictetris.view

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.classictetris.GameActivity
import com.example.classictetris.models.AppModel

class TetrisView:View {

    private val paint = Paint()
    private var lastMove:Long = 0
    private var model:AppModel? = null
    var activity: GameActivity? = null
    private val viewHandler = ViewHandler(this)
    private var cellSize:Dimension = Dimension(0, 0)
    private var frameOffset:Dimension = Dimension(0,0)

    constructor(context:Context, attributeSet: AttributeSet):
            super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyle:Int):
            super(context,attributeSet,defStyle)

    companion object {
        private val DELAY = 500
        private val BLOCK_OFFSET = 2
        private val FRAMA_OFFSET_BASE = 10
    }
}
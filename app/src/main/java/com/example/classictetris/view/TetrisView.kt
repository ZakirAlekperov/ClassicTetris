package com.example.classictetris.view

import android.content.Context
import android.graphics.*
import android.os.Message
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import com.example.classictetris.GameActivity
import com.example.classictetris.constants.CellConstants
import com.example.classictetris.constants.FieldConstants
import com.example.classictetris.constants.Motions
import com.example.classictetris.constants.Statuses
import com.example.classictetris.models.AppModel
import com.example.classictetris.models.Block
import android.os.Handler
import android.os.Looper

class TetrisView:View {

    private val paint = Paint()
    private var lastMove:Long = 0
    var model:AppModel? = null
    var activity: GameActivity? = null
    private val viewHandler = ViewHandler(this)
    private var cellSize:Dimension = Dimension(0, 0)
    private var frameOffset:Dimension = Dimension(0,0)

    constructor(context:Context, attributeSet: AttributeSet):
            super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyle:Int):
            super(context,attributeSet,defStyle)

    @JvmName("setModel1")
    fun setModel(model:AppModel){
        this.model = model
    }

    @JvmName("setActivity1")
    fun setActivity(gameActivity: GameActivity){
        this.activity = gameActivity
    }

    fun setGameCommand(move: Motions){
        if(model != null && (model?.currentState == Statuses.ACTIVE.name)){
            if(move == Motions.DOWN){
                model?.generateField(move.name)
                invalidate()
                return
            }
            setGameCommandWithDelay(move)
        }
    }

    fun setGameCommandWithDelay(move: Motions){
        val now = System.currentTimeMillis()
        if(now - lastMove > DELAY){
            model?.generateField(move.name)
            invalidate()
            lastMove = now
        }
        updateScore()
        viewHandler.sleep(DELAY.toLong())
    }

    private fun updateScore(){
        activity?.tvCurrentScore?.text = "${model?.score}"
        activity?.tvHighScore?.text = "${activity?.appPreferences?.getHighScore()}"
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFrame(canvas)
        if(model != null){
            for(i in 0 until  FieldConstants.ROW_COUNT.value){
                for(j in 0 until FieldConstants.COLUMN_COUNT.value){
                    drawCell(canvas, i, j)
                }
            }
        }
    }

    private fun drawFrame(canvas: Canvas){
        paint.color = Color.LTGRAY
        canvas.drawRect(
            frameOffset.width.toFloat(),
            frameOffset.height.toFloat(),
            width - frameOffset.width.toFloat(),
            height - frameOffset.height.toFloat(), paint
        )
    }
    private fun drawCell(canvas: Canvas, row:Int, col:Int){
        val cellStatus = model?.getCellStatus(row,col)
        if(CellConstants.EMPTY.value != cellStatus){
            val color = if(CellConstants.EPHERMERAL.value == cellStatus)
                model?.currentBlock?.getColor()
            else
                Block.getColor(cellStatus as Byte)
            drawCell(canvas, col, row, color as Int)
        }
    }

    private fun drawCell(canvas: Canvas,x:Int, y:Int, rgbColor:Int ){
        paint.color = rgbColor
        val top:Float = (frameOffset.height + y*cellSize.height + BLOCK_OFFSET).toFloat()
        val left:Float = (frameOffset.width + x*cellSize.width + BLOCK_OFFSET).toFloat()
        val bottom:Float = (frameOffset.height + (y+1)*cellSize.height + BLOCK_OFFSET).toFloat()
        val right:Float = (frameOffset.height + (x+1)*cellSize.width + BLOCK_OFFSET).toFloat()

        val rectangle= RectF(left,top,right,bottom)
        canvas.drawRoundRect(rectangle,4F, 4F, paint)
    }

    override fun onSizeChanged(width: Int, height: Int, previousWidth: Int, previousHeight: Int) {
        super.onSizeChanged(width,height,previousWidth,previousHeight)
        val cellWidth = (width -2 * FRAMA_OFFSET_BASE) / FieldConstants.COLUMN_COUNT.value
        val cellHeight = (height -2 * FRAMA_OFFSET_BASE) / FieldConstants.ROW_COUNT.value

        val n = Math.min(cellWidth,cellHeight)
        this.cellSize = Dimension(n,n)
        val offsetX = (width - FieldConstants.COLUMN_COUNT.value * n)/2
        val offsetY = (height - FieldConstants.ROW_COUNT.value * n)/2
        this.frameOffset = Dimension(offsetX,offsetY)
    }

    companion object {
        private val DELAY = 500
        private val BLOCK_OFFSET = 2
        private val FRAMA_OFFSET_BASE = 10
    }
}

private class ViewHandler(private val owner:TetrisView): Handler(Looper.myLooper()!!) {
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
        this.removeMessages(0)
        sendMessageDelayed(obtainMessage(0), delay)
    }
}
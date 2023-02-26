package com.example.classictetris.models

import android.graphics.Point
import com.example.classictetris.constants.FieldConstants
import java.util.*

class Block private constructor(private val shapeIndex: Int, private val color: BlockColor) {
    var frameNumber = 0
        private set
    var position: Point
        private set

    init {
        position = Point(FieldConstants.COLUMN_COUNT.value / 2, 0)
    }

    fun setState(frame: Int, position: Point) {
        frameNumber = frame
        this.position = position
    }

    fun getShape(frameNumber: Int): Array<ByteArray> {
        return Shape.values()[shapeIndex].getFrame(frameNumber).as2dByteArray()
    }

    val frameCount: Int
        get() = Shape.values()[shapeIndex].frameCount

    fun getColor(): Int {
        return color.rgbValue
    }

    val staticValue: Byte
        get() = color.byteValue

    companion object {
        fun createBlock(): Block {
            val random = Random()
            val shapeIndex = random.nextInt(Shape.values().size)
            val blockColor = BlockColor.values()[random.nextInt(BlockColor.values().size)]
            val block = Block(shapeIndex, blockColor)
            block.position.x = block.position.x - Shape.values()[shapeIndex].startPosition
            return block
        }

        fun getColor(value: Byte): Int {
            for (colour in BlockColor.values()) {
                if (value == colour.byteValue) {
                    return colour.rgbValue
                }
            }
            return -1
        }
    }
}
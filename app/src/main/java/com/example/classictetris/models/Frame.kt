package com.example.classictetris.models

import com.example.classictetris.helper.HelperFunctions

class Frame(private val width: Int) {
    val helperFunctions = HelperFunctions()
    val data:ArrayList<ByteArray> = ArrayList()

    fun addRow(byteString: String):Frame{
        val row = ByteArray(byteString.length)

        for (index in byteString.indices){
            row[index] = "${byteString[index]}".toByte()
        }
        data.add(row)
        return this
    }

    fun as2dByteArray():Array<ByteArray>{
        val bytes = helperFunctions.array2dofByte(data.size, width)
        return data.toArray(bytes)
    }

}
package com.example.classictetris.helper

class HelperFunctions {
    fun array2dofByte(sizeOuter: Int, sizeInner: Int):Array<ByteArray> = Array(sizeOuter){
        ByteArray(sizeInner)
    }
}
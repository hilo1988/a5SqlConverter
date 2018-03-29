package com.yoidukigembu.a5sqlparser.compiler

import com.yoidukigembu.a5sqlparser.compiler.impl.ZipCompilerImpl
import java.io.File

interface ZipCompiler {

    fun compile(outFile:File, files:Collection<File>):ByteArray

    companion object {
        var self:ZipCompiler? = null

        fun getInstance():ZipCompiler {
            if (self == null) {
                self = ZipCompilerImpl()
            }
            return self!!
        }
    }
}
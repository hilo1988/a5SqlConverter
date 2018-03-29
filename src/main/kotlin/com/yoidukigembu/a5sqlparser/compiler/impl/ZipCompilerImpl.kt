package com.yoidukigembu.a5sqlparser.compiler.impl

import com.yoidukigembu.a5sqlparser.compiler.ZipCompiler
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class ZipCompilerImpl : ZipCompiler {
    override fun compile(outFile: File, files: Collection<File>): ByteArray {
        val zip = ZipOutputStream(outFile.outputStream())
        files.forEach { file ->
            val entry = ZipEntry(file.name);
            zip.putNextEntry(entry)
            zip.write(file.readBytes())
            zip.closeEntry()
        }
        zip.flush()
        zip.close()

        return outFile.readBytes()
    }
}
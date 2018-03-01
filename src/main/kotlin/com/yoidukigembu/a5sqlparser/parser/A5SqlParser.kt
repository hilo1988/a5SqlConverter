package com.yoidukigembu.a5sqlparser.parser

import com.yoidukigembu.a5sqlparser.data.DbData
import com.yoidukigembu.a5sqlparser.parser.impl.A5SqlParserImpl
import java.io.File
import java.io.InputStream

/**
 * A5SQLパーサ
 * パースする対象は「.a5er」
 */
interface A5SqlParser {

    /**
     * ファイルパスを指定してパースする。
     */
    fun parse(file: String): DbData

    /**
     * ファイルを指定してパースする
     */
    fun parse(file: File): DbData

    fun parse(value: InputStream): DbData

    companion object {
        fun newInstance(packageName: String, baseEntityName: String?): A5SqlParser {
            return A5SqlParserImpl(packageName, baseEntityName)
        }
    }
}
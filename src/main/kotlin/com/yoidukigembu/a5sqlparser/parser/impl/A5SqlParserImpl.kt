package com.yoidukigembu.a5sqlparser.parser.impl

import com.yoidukigembu.a5sqlparser.parser.A5SqlParser
import com.yoidukigembu.a5sqlparser.data.DbData
import com.yoidukigembu.a5sqlparser.enums.Category
import com.yoidukigembu.a5sqlparser.valueobject.Column
import com.yoidukigembu.a5sqlparser.valueobject.Table
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset
import java.util.regex.Pattern

class A5SqlParserImpl(packageName:String) : A5SqlParser {

    private var currentCategory:Category? = null

    private val dbData =  DbData(packageName)

    private var currentTable:Table? = null




    override fun parse(file: String):DbData {
        return parse(File(file))
    }

    override fun parse(file: File) :DbData{
        file.readLines(Charset.defaultCharset())
                .forEach{line -> parseLine(line)}

        return dbData

    }

    override fun parse(value: InputStream): DbData {
        value.bufferedReader(Charset.defaultCharset())
                .readLines()
                .forEach{line -> parseLine(line)}

        return dbData

    }

    private fun parseLine(line:String) {

        if (line.isEmpty()) {
            return
        }

        // 行がカテゴリ行の場合はカテゴリを変更して終了
        val pattern = Pattern.compile("\\[(\\w+)\\]")
        val matcher = pattern.matcher(line)
        if (matcher.matches()) {
            currentCategory = Category.valueOf(matcher.group(1))

            if (currentCategory == Category.Entity) {
                currentTable = Table()
                dbData.addTable(currentTable!!)

            }
            return
        }

        // カテゴリが存在しない場合は処理しない
        if (currentCategory == null) {
            return
        }


        when {
            currentCategory == Category.Manager -> parseManager(line)
            currentCategory == Category.Entity -> parseEntity(line)
        }

    }

    /**
     * Managerカテゴリのパース
     */
    private fun parseManager(line: String) {
        if (!line.startsWith("CommonField=")) {
            return
        }

        dbData.commonColumns.add(Column.newInstance(line.removePrefix("CommonField=")))
    }


    /**
     * Entityカテゴリのパース
     */
    private fun parseEntity(line: String) {
        when {
            line.startsWith("PName=")
                    -> currentTable?.name = line.removePrefix("PName=")
            line.startsWith("LName=")
                    -> currentTable?.logicalName = line.removePrefix("LName=")
            line.startsWith("Field=")
                    ->currentTable?.addColumn(Column.newInstance(line.removePrefix("Field=")))

        }
    }
}
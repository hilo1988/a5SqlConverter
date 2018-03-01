package com.yoidukigembu.a5sqlparser

import com.yoidukigembu.a5sqlparser.enums.ColumnType

object Utils {

    /**
     * PHPタイプに変換
     */
    fun convertToPhpType(dbType: String):String {
        val type = convertToColumnType(dbType)

        when {
            type == ColumnType.Integer ->
                return "int"
            type == ColumnType.Long->
                    return "int"
            type == ColumnType.Double->
                    return "float"
            type == ColumnType.String->
                return "string"

            else ->
                    return "mixed"

        }
    }


    /**
     * カラムタイプに変換
     */
    fun convertToColumnType(dbType:String) : ColumnType {

        val src = dbType.removePrefix("@").toUpperCase()

        when {
            src.startsWith("CHAR") ->
                    return ColumnType.String
            src.startsWith("VARCHAR") ->
                return ColumnType.String
            src.startsWith("TEXT") ->
                    return ColumnType.String
            src.startsWith("BIGINT") ->
                    return ColumnType.Long
            src == "TIME" ->
                    return ColumnType.Time
            src == "DATE"->
                    return ColumnType.Date
            src == "DATETIME" ->
                    return ColumnType.Datetime
            src == "TIMESTAMP" ->
                    return ColumnType.Datetime
            src.startsWith("INT") ->
                    return ColumnType.Integer
            src.startsWith("DECIMAL") ->
                    return ColumnType.Decimal
            else ->
                    return ColumnType.Unknown
        }


    }
}
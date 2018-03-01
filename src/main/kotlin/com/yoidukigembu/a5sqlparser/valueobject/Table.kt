package com.yoidukigembu.a5sqlparser.valueobject

class Table {

    lateinit var logicalName: String

    lateinit var name: String

    var columns: MutableList<Column> = mutableListOf()

    fun addColumn(column: Column) {
        columns?.add(column)
    }


}
package com.yoidukigembu.a5sqlparser.data

import com.yoidukigembu.a5sqlparser.valueobject.Column
import com.yoidukigembu.a5sqlparser.valueobject.Table

class DbData(val packageName:String, var baseEntityName:String?) {

    var tables = mutableListOf<Table>()
    private set

    var commonColumns = ArrayList<Column>()

    fun addTable(table: Table) {
        tables.add(table)
    }


}
package com.yoidukigembu.a5sqlparser.valueobject

import org.apache.commons.csv.CSVFormat
import org.apache.commons.lang3.math.NumberUtils
import java.io.IOException
import java.io.StringReader


/**
 * カラムデータ
 */
data class Column(val logicalName:String,
                  val columnName:String,
                  val dbType:String,
                  val idFlg:Boolean) {


    fun getPhpValueType() : String {
        return ""
    }


    companion object {
        fun newInstance(line:String):Column {
            try  {
                val reader = StringReader(line)
                val row = CSVFormat.DEFAULT.parse(reader).records[0]

                val logicalName = row[0]
                val columnName = row[1]
                val dbType = row[2]
                val idFlgStr = row[4]


                return Column(logicalName, columnName, dbType, NumberUtils.isDigits(idFlgStr))
            } catch (e:IOException) {
                throw e
            }
        }
    }
}
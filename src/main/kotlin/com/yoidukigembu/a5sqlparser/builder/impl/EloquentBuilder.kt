package com.yoidukigembu.a5sqlparser.builder.impl

import com.google.common.base.CaseFormat
import com.yoidukigembu.a5sqlparser.builder.DbObjectBuilder
import com.yoidukigembu.a5sqlparser.data.DbData
import com.yoidukigembu.a5sqlparser.valueobject.Column
import com.yoidukigembu.a5sqlparser.valueobject.Table
import org.jboss.dna.common.text.Inflector
import java.io.File
import java.nio.charset.Charset

class EloquentBuilder(private val dbData: DbData) : DbObjectBuilder {


    private val dir = File(System.getProperty("java.io.tmpdir"), System.nanoTime().toString())

    private val accessorTemplate: String

    private val tableTemplate: String


    init {
        val classLoader = this.javaClass.classLoader
        accessorTemplate = classLoader.getResourceAsStream("eloquent/GetterSetter.txt")
                .reader()
                .readText()

        tableTemplate = classLoader.getResourceAsStream("eloquent/Table.txt")
                .reader()
                .readText()
    }

    override fun build() {

        dir.mkdirs()

        print("dir:" + dir.absolutePath)
        createBaseEntity()

        dbData.tables.forEach { table -> createEntity(table) }



    }


    /**
     * ベースエンティティの作成
     */
    private fun createBaseEntity() {

        val template = this.javaClass.classLoader.getResourceAsStream("eloquent/Base.txt")
                .reader(Charset.defaultCharset())
                .readText()

        val const = createColumnConstants(dbData.commonColumns)

        val accessors = createAccessors(dbData.commonColumns)

        val baseEntity = template.replace("%namespace%", dbData.packageName)
                .replace("%columnConst%", const)
                .replace("%getterSetter%", accessors)


        val file = File(dir, "BaseModel.php");
        file.writeText(baseEntity)

    }

    private fun createEntity(table: Table) {
        val className = CaseFormat.LOWER_UNDERSCORE.to(
                CaseFormat.UPPER_CAMEL, Inflector.getInstance().singularize(table.name))

        val columns = table.columns.toList().filter { c -> !c.idFlg }
        val const = createColumnConstants(columns)

        val accessors = createAccessors(columns)

        val fillables = columns
                .plus(dbData.commonColumns)
                .map { c -> "self::%s".format(c.columnName.toUpperCase()) }
                .joinToString(",\n")


        val table = tableTemplate
                .replace("%namespace%", dbData.packageName)
                .replace("%logicalName%", table.logicalName)
                .replace("%className%", className)
                .replace("%tableName%", table.name)
                .replace("%columnConst%", const)
                .replace("%fillables%", fillables)
                .replace("%getterSetter%", accessors)


        File(dir, className + ".php")
                .writeText(table)

    }


    private fun createAccessors(columns: List<Column>): String {
        return columns
                .map { c -> convertToGetterSetter(c) }
                .joinToString("\n")
    }

    /**
     * Getter/Setterの作成
     */
    private fun convertToGetterSetter(column: Column): String {
        val upperCamelColumn = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, column.columnName);
        return accessorTemplate.replace("%logicalName%", column.logicalName)
                .replace("%UpperColumnName%", upperCamelColumn)
                .replace("%constColumn%", String.format("self::%s", column.columnName.toUpperCase()))
                .replace("%dbType%", column.dbType)
    }


    private fun createColumnConstants(columns: List<Column>): String {
        return columns.map { c -> String.format("/** %s */\nconst %s = '%s';", c.logicalName, c.columnName.toUpperCase(), c.columnName) }
                .joinToString("\n")

    }


}
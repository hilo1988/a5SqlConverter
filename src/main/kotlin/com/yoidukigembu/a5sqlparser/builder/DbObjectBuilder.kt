package com.yoidukigembu.a5sqlparser.builder

import com.yoidukigembu.a5sqlparser.builder.impl.EloquentBuilder
import com.yoidukigembu.a5sqlparser.data.DbData

/**
 * DB用オブジェクトビルダー
 */
interface DbObjectBuilder {

    /**
     * DBオブジェクトを生成し、zipに圧縮したバイトデータを返す
     */
    fun build(): ByteArray

    companion object {
        fun getEloquentBuilder(dbData: DbData): DbObjectBuilder {
            return EloquentBuilder(dbData)
        }
    }
}
package com.sunfusheng.github.kotlin.util

/**
 * @author sunfusheng
 * @since 2019-12-30
 */
object CollectionUtil {

    fun isEmpty(collection: Collection<*>?): Boolean {
        return null == collection || collection.isEmpty()
    }

    fun isEmpty(map: Map<*, *>?): Boolean {
        return null == map || map.isEmpty()
    }

    fun isEmpty(array: Array<Any?>?): Boolean {
        return null == array || array.isEmpty()
    }

    fun isEmpty(array: IntArray?): Boolean {
        return null == array || array.isEmpty()
    }

    fun isEmpty(array: ShortArray?): Boolean {
        return null == array || array.isEmpty()
    }

    fun isEmpty(array: LongArray?): Boolean {
        return null == array || array.isEmpty()
    }

    fun isEmpty(array: FloatArray?): Boolean {
        return null == array || array.isEmpty()
    }

    fun isEmpty(array: DoubleArray?): Boolean {
        return null == array || array.isEmpty()
    }

    fun isEmpty(array: ByteArray?): Boolean {
        return null == array || array.isEmpty()
    }

    fun isEmpty(array: CharArray?): Boolean {
        return null == array || array.isEmpty()
    }

    fun isEmpty(array: BooleanArray?): Boolean {
        return null == array || array.isEmpty()
    }

    fun getSize(collection: Collection<*>?): Int {
        return collection?.size ?: 0
    }
}
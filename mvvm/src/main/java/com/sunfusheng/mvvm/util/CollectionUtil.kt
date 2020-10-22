package com.sunfusheng.mvvm.util

/**
 * @author sunfusheng
 * @since 2019-12-30
 */
object CollectionUtil {

    fun isEmpty(collection: Collection<*>?) = null == collection || collection.isEmpty()

    fun isEmpty(map: Map<*, *>?) = null == map || map.isEmpty()

    fun isEmpty(array: Array<Any?>?) = null == array || array.isEmpty()

    fun isEmpty(array: IntArray?) = null == array || array.isEmpty()

    fun isEmpty(array: ShortArray?) = null == array || array.isEmpty()

    fun isEmpty(array: LongArray?) = null == array || array.isEmpty()

    fun isEmpty(array: FloatArray?) = null == array || array.isEmpty()

    fun isEmpty(array: DoubleArray?) = null == array || array.isEmpty()

    fun isEmpty(array: ByteArray?) = null == array || array.isEmpty()

    fun isEmpty(array: CharArray?) = null == array || array.isEmpty()

    fun isEmpty(array: BooleanArray?) = null == array || array.isEmpty()

    fun getSize(collection: Collection<*>?) = collection?.size ?: 0
}
package com.mtzqc.kv

import android.os.Parcelable

interface IStore {
    fun removeKey(key: String): Boolean

    fun getInt(key: String, defaultValue: Int): Int

    fun putInt(key: String, value: Int)

    fun getLong(key: String, defaultValue: Long): Long

    fun putLong(key: String, value: Long)

    fun getFloat(key: String, defaultValue: Float): Float

    fun putFloat(key: String, value: Float)

    fun getDouble(key: String, defaultValue: Double): Double

    fun putDouble(key: String, value: Double)

    fun getBool(key: String, defaultValue: Boolean): Boolean

    fun putBool(key: String, value: Boolean)

    fun getStr(key: String, defaultValue: String? = null): String?

    fun putStr(key: String, value: String)

    fun getSetStr(key: String, defaultValue: Set<String>? = null): Set<String>?

    fun putSetStr(key: String, value: Set<String>)

    fun <P : Parcelable> getParcelable(key: String, pClazz: Class<P>, defaultValue: P? = null): P?

    fun putParcelable(key: String, p: Parcelable)

    fun  getByteArray(key: String,  defaultValue:ByteArray? = null): ByteArray?

    fun putByteArray(key: String, array: ByteArray)
}
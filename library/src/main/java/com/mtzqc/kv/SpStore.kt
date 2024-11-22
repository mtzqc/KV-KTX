package com.mtzqc.kv

import android.content.SharedPreferences
import android.os.Parcelable
import java.io.UnsupportedEncodingException

abstract class SpStore : IStore {
    protected open fun edit(): SharedPreferences.Editor {
        return sp().edit()
    }

    protected abstract fun sp(): SharedPreferences

    protected open fun commitStore(edit: SharedPreferences.Editor) {
        edit.commit()
    }

    override fun removeKey(key: String): Boolean {
        commitStore(edit().remove(key))
        return true
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return sp().getInt(key, defaultValue)
    }

    override fun putInt(key: String, value: Int) {
        commitStore(edit().putInt(key, value))
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return sp().getLong(key, defaultValue)
    }

    override fun putLong(key: String, value: Long) {
        commitStore(edit().putLong(key, value))
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return sp().getFloat(key, defaultValue)
    }

    override fun putFloat(key: String, value: Float) {
        commitStore(edit().putFloat(key, value))
    }

    override fun getDouble(key: String, defaultValue: Double): Double {
        val str = getStr(key)
        return str?.toDouble() ?: defaultValue
    }

    override fun putDouble(key: String, value: Double) {
        putStr(key, value.toString())
    }

    override fun getBool(key: String, defaultValue: Boolean): Boolean {
        return sp().getBoolean(key, defaultValue)
    }

    override fun putBool(key: String, value: Boolean) {
        commitStore(edit().putBoolean(key, value))
    }

    override fun getStr(key: String, defaultValue: String?): String? {
        return sp().getString(key, defaultValue)
    }

    override fun putStr(key: String, value: String) {
        commitStore(edit().putString(key, value))
    }

    override fun getSetStr(key: String, defaultValue: Set<String>?): Set<String>? {
        return sp().getStringSet(key, defaultValue)
    }

    override fun putSetStr(key: String, value: Set<String>) {
        commitStore(edit().putStringSet(key, value))
    }

    override fun <P : Parcelable> getParcelable(
        key: String,
        pClazz: Class<P>,
        defaultValue: P?
    ): P? {
        throw UnsupportedEncodingException("not support getParcelable ${pClazz.name} with key $key")
    }

    override fun putParcelable(key: String, p: Parcelable) {
        throw UnsupportedEncodingException("not support putParcelable with key $key value $p")
    }

    override fun getByteArray(key: String, defaultValue: ByteArray?): ByteArray? {
        return getStr(key)?.toByteArray(Charsets.UTF_8) ?: defaultValue
    }

    override fun putByteArray(key: String, array: ByteArray) {
        val str = String(array, Charsets.UTF_8)
        putStr(key, str)
    }
}
package com.mtzqc.kv

import android.content.SharedPreferences
import android.os.Parcelable
import com.tencent.mmkv.MMKV

abstract class MMkvStore : SpStore() {
    protected abstract fun kv(): MMKV
    override fun sp(): SharedPreferences {
        return kv()
    }

    override fun commitStore(edit: SharedPreferences.Editor) {

    }

    override fun getDouble(key: String, defaultValue: Double): Double {
        return kv().decodeDouble(key, defaultValue)
    }

    override fun putDouble(key: String, value: Double) {
        kv().encode(key, value)
    }

    override fun putParcelable(key: String, p: Parcelable) {
        kv().encode(key, p)
    }

    override fun <P : Parcelable> getParcelable(
        key: String,
        pClazz: Class<P>,
        defaultValue: P?
    ): P? {
        return kv().decodeParcelable(key, pClazz, defaultValue)
    }

    override fun getByteArray(key: String, defaultValue: ByteArray?): ByteArray? {
        return kv().decodeBytes(key, defaultValue)
    }

    override fun putByteArray(key: String, array: ByteArray) {
        kv().encode(key, array)
    }
}
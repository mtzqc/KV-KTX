package com.mtzqc.kv.coder

import com.mtzqc.kv.SerializableUtil
import java.io.Serializable


class SerializableCoder : IEncoder, IDecoder {
    override val order: Int
        get() = -1

    override fun isSupport(key: String, any: Any): Boolean {
        return any is Serializable
    }

    override fun encode(key: String, any: Any): ByteArray {
        return SerializableUtil.encode(any as Serializable)!!
    }

    override fun isSupport(key: String, clazz: Class<*>): Boolean {
        return clazz.isAssignableFrom(Serializable::class.java)
    }

    override fun <T> decode(key: String, array: ByteArray, clazz: Class<T>): T {
        return SerializableUtil.decode<T>(array)!!
    }
}
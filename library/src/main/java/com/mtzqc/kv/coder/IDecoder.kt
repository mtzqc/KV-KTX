package com.mtzqc.kv.coder

interface IDecoder : ICoder {
    fun isSupport(key: String, clazz: Class<*>): Boolean
    fun <T> decode(key: String, array: ByteArray, clazz: Class<T>): T
}
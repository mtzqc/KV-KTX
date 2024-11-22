package com.mtzqc.kv.coder

interface IEncoder : ICoder {
    fun isSupport(key: String, any: Any): Boolean
    fun encode(key: String, any: Any): ByteArray
}
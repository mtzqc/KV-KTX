package com.mtzqc.kv

import com.mtzqc.kv.coder.ICoder
import com.mtzqc.kv.coder.IDecoder
import com.mtzqc.kv.coder.IEncoder


object KvPlugins {
    private val encoderList = mutableListOf<IEncoder>()
    private val decoderList = mutableListOf<IDecoder>()

    fun addCoder(coder: ICoder) {
        if (coder is IEncoder && !encoderList.contains(coder)) {
            val newList = encoderList.toMutableList()
            newList.add(coder)
            newList.sort()
            encoderList.clear()
            encoderList.addAll(newList)
        }
        if (coder is IDecoder && !decoderList.contains(coder)) {
            val newList = decoderList.toMutableList()
            newList.add(coder)
            newList.sort()
            decoderList.clear()
            decoderList.addAll(newList)
        }
    }

    fun removeCoder(coder: ICoder) {
        if (coder is IEncoder) {
            encoderList.remove(coder)
        }
        if (coder is IDecoder) {
            decoderList.remove(coder)
        }
    }

    fun findDecoder(key: String, clazz: Class<*>): IDecoder? {
        val iterator = decoderList.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.isSupport(key, clazz)) {
                return next
            }
        }
        return null
    }

    fun findEncoder(key: String, any: Any): IEncoder? {
        val iterator = encoderList.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.isSupport(key, any)) {
                return next
            }
        }
        return null
    }

}
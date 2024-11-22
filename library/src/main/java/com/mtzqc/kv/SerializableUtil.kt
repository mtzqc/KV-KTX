package com.mtzqc.kv

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

object SerializableUtil {
    fun <V> decode(array: ByteArray?): V? {
        if (null == array || array.isEmpty()) {
            return null
        }
        return ObjectInputStream(ByteArrayInputStream(array)).use {
            @Suppress("UNCHECKED_CAST")
            it.readObject() as V
        }
    }

    fun encode(value: Serializable?): ByteArray? {
        if(null==value){
            return null
        }
        val stream = ByteArrayOutputStream()
        return ObjectOutputStream(stream).use {
            it.writeObject(value)
            it.flush()
            stream.toByteArray()
        }
    }
}
package com.mtzqc.kv.coder

import com.google.gson.Gson
import com.google.gson.GsonBuilder

open class GsonCoder : IEncoder, IDecoder {
    private var _gson: Gson? = null

    protected open fun buildGson(): Gson {
        return GsonBuilder().disableHtmlEscaping().create();
    }

    private fun gsonValue(): Gson {
        if (null == _gson) {
            synchronized(this) {
                if (null == _gson) {
                    _gson = buildGson()
                }
            }
        }
        return _gson!!
    }


    override fun isSupport(key: String, any: Any): Boolean {
        return true
    }

    /* override fun encode(key: String, any: Any): String {
         return gsonValue().toJson(any)
     }*/
    override fun encode(key: String, any: Any): ByteArray {
         return  gsonValue().toJson(any).toByteArray(Charsets.UTF_8)
    }

    override fun <T> decode(key: String, array: ByteArray, clazz: Class<T>): T {
        return  gsonValue().fromJson(String(array,Charsets.UTF_8),clazz)
    }

    override fun isSupport(key: String, clazz: Class<*>): Boolean {
        return true
    }

    override val order: Int
        get() = -2
}
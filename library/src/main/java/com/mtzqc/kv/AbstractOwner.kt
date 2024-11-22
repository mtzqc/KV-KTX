package com.mtzqc.kv

abstract class AbstractOwner(prefix: String? = null, appendClazz: Boolean = false) : IKVOwner {
    private val prefixKey: String? = if (appendClazz) {
        if (prefix.isNullOrEmpty()) {
            javaClass.name
        } else {
            "${javaClass.name}${prefix}"
        }
    } else {
        prefix
    }

    /**
     * 不建议动态重写方法做动态改变，如果需要根据UserId,环境时，通过构建不同的Owner实现
     */
    override fun dynamicKey(key: String): String {
        return if (null == prefixKey) super.dynamicKey(key) else "${prefixKey}_$key"
    }

}
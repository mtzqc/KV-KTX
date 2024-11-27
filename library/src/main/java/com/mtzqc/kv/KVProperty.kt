package com.mtzqc.kv

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private object UnValue
class KVProperty<V>(
    private val useCache: Boolean?,
    private val getter: (String) -> V,
    private val setter: Pair<String, V>.() -> Unit
) : ReadWriteProperty<IKVOwner, V> {

    private fun realKey(thisRef: IKVOwner, key: String): String {
        return thisRef.dynamicKey(key)
    }
    private val useMemory:Boolean
        get() = KvPlugins.useGlobalCache(useCache)

    private var cacheValue: Any? = UnValue
    override fun getValue(thisRef: IKVOwner, property: KProperty<*>): V {
        if (useMemory) {
            if (cacheValue != UnValue) {
                @Suppress("UNCHECKED_CAST")
                return cacheValue as V
            }
        }
        val realKey = realKey(thisRef, property.name)
        return getter.invoke(realKey).also {
            if (useMemory) {
                cacheValue = it
            }
        }
    }

    override fun setValue(thisRef: IKVOwner, property: KProperty<*>, value: V) {
        if (null == value) {
            cacheValue =UnValue
            thisRef.removeKey(property.name)
        } else {
            setter.invoke(realKey(thisRef, property.name) to value)
            cacheValue = value
        }
    }
}
package com.mtzqc.kv

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class KVProperty<V>(
    private val getter: (String) -> V,
    private val setter: Pair<String, V>.() -> Unit
) : ReadWriteProperty<IKVOwner, V> {
    private fun realKey(thisRef: IKVOwner, key: String): String {
        return thisRef.dynamicKey(key)
    }

    override fun getValue(thisRef: IKVOwner, property: KProperty<*>): V {
        val realKey = realKey(thisRef, property.name)
        return getter.invoke(realKey)
    }

    override fun setValue(thisRef: IKVOwner, property: KProperty<*>, value: V) {
        if (null == value) {
            thisRef.removeKey(property.name)
        } else {
            setter.invoke(realKey(thisRef, property.name) to value)
        }
    }
}
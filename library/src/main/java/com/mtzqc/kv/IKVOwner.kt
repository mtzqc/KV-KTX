package com.mtzqc.kv

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import java.lang.UnsupportedOperationException
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

interface IKVOwner {
    val store: IStore

    fun dynamicKey(key: String): String {
        return key
    }

    fun removeKey(key: String): Boolean {
        return removeRealKey(dynamicKey(key))
    }

    fun removeRealKey(realKey: String): Boolean {
        return store.removeKey(realKey)
    }
}


fun <T> IKVOwner.removeProperty(proper: KProperty<T>) = removeKey(proper.name)

fun IKVOwner.kvInt(default: Int = 0) = KVProperty({
    store.getInt(it, default)
}, {
    store.putInt(first, second)
})

fun IKVOwner.kvLong(default: Long = 0L) = KVProperty({
    store.getLong(it, default)
}, {
    store.putLong(first, second)
})

fun IKVOwner.kvFloat(default: Float = 0F) = KVProperty({
    store.getFloat(it, default)
}, {
    store.putFloat(first, second)
})

fun IKVOwner.kvDouble(default: Double = 0.0) = KVProperty({
    store.getDouble(it, default)
}, {
    store.putDouble(first, second)
})

fun IKVOwner.kvBool(default: Boolean = false) = KVProperty({
    store.getBool(it, default)
}, {
    store.putBool(first, second)
})

fun IKVOwner.kvStr(default: String? = null) = KVProperty({
    store.getStr(it, default)
}, {
    store.putStr(first, second!!)
})

fun IKVOwner.kvSet(default: Set<String>? = null) = KVProperty({
    store.getSetStr(it, default)
}, {
    store.putSetStr(first, second!!)
})

fun IKVOwner.kvBytes(default: ByteArray? = null) = KVProperty({
    store.getByteArray(it, default)
}, {
    store.putByteArray(first, second!!)
})


inline fun <reified P : Parcelable> IKVOwner.kvParcelable(default: P? = null) = KVProperty({
    store.getParcelable(it, P::class.java, default)
}, {
    store.putParcelable(first, second!!)
})

inline fun <reified V> IKVOwner.kvCoder(default: V? = null) = KVProperty({
    val decoder = KvPlugins.findDecoder(it, V::class.java)
        ?: throw UnsupportedOperationException("un support decoder key: $it clazz : ${V::class.java.name}")
    val array = store.getByteArray(it) ?: return@KVProperty default
    return@KVProperty decoder.decode(it, array, V::class.java)
}, {
    val encoder = KvPlugins.findEncoder(first, second!!)
        ?: throw UnsupportedOperationException("un support encoder key: $first clazz : ${V::class.java.name}")
    val array = encoder.encode(first, second!!)
    store.putByteArray(first, array)
})


@Suppress("UNCHECKED_CAST")
fun <V> KVProperty<V?>.asNotNull(): KVProperty<V> = this as KVProperty<V>

@Suppress("UNCHECKED_CAST")
fun <V> KVProperty<V>.asRemove(): KVProperty<V?> = this as KVProperty<V?>


fun <V> KVProperty<V>.asLiveData() = object : ReadOnlyProperty<IKVOwner, MutableLiveData<V>> {
    private var cache: MutableLiveData<V>? = null
    override fun getValue(thisRef: IKVOwner, property: KProperty<*>): MutableLiveData<V> {
        if (null == cache) {
            synchronized(this@asLiveData) {
                if (null == cache) {
                    cache = object : MutableLiveData<V>() {
                        override fun getValue() = this@asLiveData.getValue(thisRef, property)

                        override fun setValue(value: V) {
                            if (super.getValue() != value) {
                                this@asLiveData.setValue(thisRef, property, value)
                            }
                            super.setValue(value)
                        }

                        override fun onActive() = super.setValue(value)
                    }
                }

            }
        }
        return cache!!
    }
}

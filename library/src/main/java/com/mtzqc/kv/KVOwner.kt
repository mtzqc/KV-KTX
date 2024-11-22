package com.mtzqc.kv


open class KVOwner(
    private val factory: () -> IStore,
    prefix: String? = null,
    appendClazz: Boolean = false,
) : AbstractOwner(prefix, appendClazz) {
    private val lazyStore: IStore by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        factory.invoke()
    }
    override val store: IStore
        get() = lazyStore
}
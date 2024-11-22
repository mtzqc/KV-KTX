package com.mtzqc.kv

import android.content.SharedPreferences
import com.tencent.mmkv.MMKV

class MMkvImpl(private val factory: () -> MMKV) : MMkvStore() {

    constructor(sp: MMKV) : this({
        sp
    })

    constructor() : this({
        MMKV.defaultMMKV()
    })


    private val kvValue: MMKV by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        factory.invoke()
    }

    override fun sp(): SharedPreferences {
        return kv()
    }

    override fun kv(): MMKV {
        return kvValue
    }
}
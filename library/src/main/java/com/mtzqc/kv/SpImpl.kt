package com.mtzqc.kv

import android.content.Context
import android.content.SharedPreferences

class SpImpl(private val factory: () -> SharedPreferences) : SpStore() {
    constructor(context: Context, name: String, mode: Int) : this({
        context.getSharedPreferences(
            name,
            mode
        )
    })

    constructor(sp: SharedPreferences) : this({
        sp
    })

    private val spValue: SharedPreferences by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        factory.invoke()
    }

    override fun sp(): SharedPreferences {
        return spValue
    }
}
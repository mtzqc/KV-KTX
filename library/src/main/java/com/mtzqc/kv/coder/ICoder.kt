package com.mtzqc.kv.coder

interface ICoder : Comparable<ICoder> {
    val order: Int
        get() = 0

    override fun compareTo(other: ICoder): Int {
        return order - other.order
    }
}
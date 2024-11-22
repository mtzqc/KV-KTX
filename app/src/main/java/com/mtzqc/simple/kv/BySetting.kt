package com.mtzqc.simple.kv

import com.mtzqc.kv.IKVOwner
import com.mtzqc.kv.KVOwner
import com.mtzqc.kv.MMkvImpl


class BySetting: UnBase(), IKVOwner by KVOwner({
    MMkvImpl()
}) {

}
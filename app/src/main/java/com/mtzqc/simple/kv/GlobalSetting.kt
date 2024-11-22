package com.mtzqc.simple.kv

import com.mtzqc.kv.KVOwner
import com.mtzqc.kv.MMkvImpl
import com.mtzqc.kv.kvBool
import com.mtzqc.kv.kvCoder


object GlobalSetting : KVOwner({
    MMkvImpl()
}) {
    var isLogin by kvBool()

    var loginUser by kvCoder<UserInfo>()
}
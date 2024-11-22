package com.mtzqc.simple.kv

import com.mtzqc.kv.KVOwner
import com.mtzqc.kv.MMkvImpl
import com.mtzqc.kv.asLiveData
import com.mtzqc.kv.asRemove
import com.mtzqc.kv.kvBool
import com.mtzqc.kv.kvInt
import com.mtzqc.kv.kvLong
import com.mtzqc.kv.kvStr
import com.mtzqc.kv.removeProperty

class UserSetting(userId: String) : KVOwner({
    MMkvImpl()
}, userId) {
    var userName by kvStr()
    var age by kvInt()
    var gender by kvBool().asRemove()
    val time by kvLong().asRemove().asLiveData()

    fun removeTest(){
        removeProperty(UserSetting::userName)
    }

    fun toStr():String{
        return "{userName: ${userName}, age: $age,  gender: ${gender}, time: ${time.value}}"
    }
}
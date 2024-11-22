package com.mtzqc.simple

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mtzqc.kv.KvPlugins
import com.mtzqc.kv.coder.GsonCoder
import com.mtzqc.kv.coder.SerializableCoder
import com.mtzqc.simple.kv.GlobalSetting
import com.mtzqc.simple.kv.UserInfo
import com.mtzqc.simple.kv.UserSetting
import com.tencent.mmkv.MMKV
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    val userA by lazy {
        UserSetting("111")
    }
    val userB by lazy {
        UserSetting("222")
    }

    lateinit var showTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mtzqc.simple.R.layout.activity_main)
        MMKV.initialize(applicationContext)
        KvPlugins.addCoder(SerializableCoder())
        KvPlugins.addCoder(GsonCoder())
        showTxt = findViewById(com.mtzqc.simple.R.id.valueTx)
        bindClick(com.mtzqc.simple.R.id.loginBtn) {
            GlobalSetting.isLogin = !GlobalSetting.isLogin
        }
        bindClick(com.mtzqc.simple.R.id.user) {
            GlobalSetting.loginUser = if (it % 2 == 0) null else UserInfo().also { u ->
                u.age = it
            }
        }

        bindClick(com.mtzqc.simple.R.id.aName) {
            userA.userName = if (it % 4 == 0) null else it.toString()
        }
        bindClick(com.mtzqc.simple.R.id.aAge) {
            userA.age = it
        }
        bindClick(com.mtzqc.simple.R.id.aGender) {
            userA.gender = if (it % 3 == 0) null else it % 2 == 0
        }

        bindClick(com.mtzqc.simple.R.id.aTime) {
            userA.time.value = if (it % 4 == 0) null else it.toLong()
        }

        bindClick(com.mtzqc.simple.R.id.bName) {
            userB.userName = if (it % 4 == 0) null else it.toString()
        }
        bindClick(com.mtzqc.simple.R.id.bAge) {
            userB.age = it
        }
        bindClick(com.mtzqc.simple.R.id.bGender) {
            userB.gender = it % 2 == 0
        }

        bindClick(com.mtzqc.simple.R.id.bTime) {
            userB.time.value = if (it % 4 == 0) null else it.toLong()
        }
        userA.time.observe(this) {
            Toast.makeText(this, "userA time: $it", Toast.LENGTH_SHORT).show()
        }
        userB.time.observe(this) {
            Toast.makeText(this, "userB time: $it", Toast.LENGTH_SHORT).show()
        }
        showValue()

    }

    private fun bindClick(id: Int, block: (Int) -> Unit) {
        findViewById<View>(id).setOnClickListener {
            block.invoke(abs(Random.nextInt()))
            showValue()
        }
    }

    fun showValue() {
        val sb = StringBuilder()
        sb.append("Global  isLogin : ").appendLine(GlobalSetting.isLogin)
            .append("userA  ").appendLine(userA.toStr())
            .append("userB  ").appendLine(userB.toStr())
            .appendLine(GlobalSetting.loginUser)
        showTxt.text = sb.toString()
    }
}
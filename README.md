# KV-KTX

站在大佬的肩膀上，参考了大佬的 [MMKV-KTX](https://github.com/DylanCaiCoding/MMKV-KTX) 感谢大佬  
[![](https://jitpack.io/v/mtzqc/kv-ktx.svg)](https://jitpack.io/#mtzqc/kv-ktx)
[![License](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](https://github.com/DylanCaiCoding/MMKV-KTX/blob/master/LICENSE)

1、结合了 Kotlin 属性委托的特性，使用field属性名作为缓存key，无需声明大量的键名常量  
2、支持多套环境维护，互相切换，互不影响  
3、支持任意对象缓存，需要自定义序列化，[SerializableCoder](library/src/main/java/com/mtzqc/kv/coder/SerializableCoder.kt),[GsonCoder](library/src/main/java/com/mtzqc/kv/coder/GsonCoder.kt)开箱即用，可自定义其他coder

store为真正的缓存实现，默认的实现有  
[SpImpl](library/src/main/java/com/mtzqc/kv/SpImpl.kt) 作为SharedPreferences的实现  
[MMkvImpl](library/src/main/java/com/mtzqc/kv/MMkvImpl.kt) 作为 [MMKV](https://github.com/Tencent/MMKV) 实现(推荐使用)  
如果都不满足您的需求，您也可以自定义实现[IStore](library/src/main/java/com/mtzqc/kv/IStore.kt)即可

## 快速入门

在根目录的 `build.gradle` 添加:

```groovy
allprojects {
    repositories {
        //...
        maven { url 'https://www.jitpack.io' }
    }
}
```

在模块的 `build.gradle` 添加依赖：

```groovy
dependencies {
    implementation 'com.github.mtzqc:kv-ktx:1.0.0'
    //如果使用mmkv,不使用可以不引入
    implementation 'com.tencent:tencent:xxxx'
}
```

让一个类继承`KVOwner` 即可在该类使用 `by kvXXX()` 函数将属性委托给 `Store`，例如：

```kotlin
object GlobalSetting : KVOwner({
    MMkvImpl()
}) {
    var isLogin by kvBool()

    var loginUser by kvCoder<UserInfo>()
}
```

如果已经有了父类继承不了，那就实现 `IKVOwner by KVOwner`，比如：

```kotlin
class BySetting: UnBase(),IKVOwner by KVOwner({
    MMkvImpl()
}) {

}
```

支持以下类型：

| 函数        | 默认值        |
| --------- |------------|
| `kvInt()` | 0          |
| `kvLong()` | 0L         |
| `kvBool()` | false      |
| `kvFloat()` | 0f         |
| `kvDouble()` | 0.0        |
| `kvStr()` | /          |
| `kvSet()` | /          |
| `kvBytes()` | /          |
| `kvParcelable()` | SpStore不支持 |
| `kvCoder()` | 自定义序列化     |

**如果不同IKVOwner的子类中定义了相同的字段，避免重复，务必保证AbstractOwner中的prefix唯一，或者使用不相同的store,或者appendClazz=true, 三选一**

进阶用法：

环境隔离
```kotlin
val userA by lazy {
    UserSetting("111")
}
val userB by lazy {
    UserSetting("222")
}
userA.userName = "张三"
userB.userName = "李四"
```

asRemove()转换，可以使用null删除缓存操作，缓存删除后再次获取任然返回默认值
```kotlin 
    var gender by kvBool().asRemove()
    //删除缓存
    userA.gender = null
```

asLiveData()转换，可以使用LiveData的方式操作缓存
```kotlin 
      val time by kvLong().asRemove().asLiveData()
      userA.time.value = 10L
      userA.time.postValue(100L)
      //删除缓存
      userA.time.value = null
    
```

删除缓存
```kotlin 
    //方法1
    fun removeTest(){
        removeProperty(UserSetting::userName)
    }
    //方法2
    userA.gender = null
    //方法3
    livedata.value = null
    livedata.postValue(null)
```
kvCoder(),在使用前，必须先执行KvPlugins.addCoder()，如果使用时找不到对应的coder会抛异常,根据自身需求添加，也可以自定义实现，如protobuf，Fastjson
```kotlin 
    //使用
    var loginUser by kvCoder<UserInfo>()
    //非必须 java序列化
    // KvPlugins.addCoder(SerializableCoder())
    //非必须 Gson序列化,记得 implementation 'com.google.code.gson:gson:xxx'
    KvPlugins.addCoder(GsonCoder())
    //KvPlugins.addCoder(XXXCoder())
    //KvPlugins.addCoder(XXXCoder())
```


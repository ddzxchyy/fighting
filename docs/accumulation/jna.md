# Java Native Access (JNA)

[jna github 地址](https://github.com/java-native-access/jna)



## 简介



## 2. 快速入门

### 2.1 环境准备 


```xml
<dependency>
   <groupId>net.java.dev.jna</groupId>
   <artifactId>jna</artifactId>
   <version>4.0.0</version>
</dependency>
```

### 2.2 示例

使用 JNA 实现打印函数

```java
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/** Simple example of JNA interface mapping and usage. */
public class HelloWorld {

    // This is the standard, stable way of mapping, which supports extensive
    // customization and mapping of Java to native types.

    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary)
            Native.load((Platform.isWindows() ? "msvcrt" : "c"),
                                CLibrary.class);

        void printf(String format, Object... args);
    }

    public static void main(String[] args) {
        CLibrary.INSTANCE.printf("Hello, World\n");
        for (int i=0;i < args.length;i++) {
            CLibrary.INSTANCE.printf("Argument %d: %s\n", i, args[i]);
        }
    }
}
```



通过扩展 Library 接口来声明一个 Java 接口，对应本地方法。

```java
public interface Kernel32 extends StdCallLibrary {
    Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("C:\\Users\\ddzxchyy\\Desktop\\sdk\\RecordCtrl.dll", Kernel32.class);

    int ky_record_init_server(String ip, Integer port);
}
```

### 2.3 默认映射

| Native Type | Size                | Java Type  | Common Windows Types    |
| ----------- | ------------------- | ---------- | ----------------------- |
| char        | 8-bit integer       | byte       | BYTE, TCHAR             |
| short       | 16-bit integer      | short      | WORD                    |
| wchar_t     | 16/32-bit character | char       | TCHAR                   |
| int         | 32-bit integer      | int        | DWORD                   |
| int         | boolean value       | boolean    | BOOL                    |
| long        | 32/64-bit integer   | NativeLong | LONG                    |
| long long   | 64-bit integer      | long       | __int64                 |
| float       | 32-bit FP           | float      |                         |
| double      | 64-bit FP           | double     |                         |
| char*       | C string            | String     | LPCSTR                  |
| void*       | pointer             | Pointer    | LPVOID, HANDLE, LP*XXX* |





## 3 进阶

### 3.1 回调



!> 通过 `Native.setCallbackThreadInitializer` 方法将 `CallbackThreadInitializer` 与每个回调相关联。这使他们都在同一个线程上。如果你有一个完成的回调或者你发送了一些标志,表明这个回调是你可以调用 Native.detatch(true) 的最后一个,以便分离线程并让它被 gc 清理。

```java

CallbackThreadInitializer callbackThreadInitializer = new CallbackThreadInitializer();
// callback 回调的接口
Native.setCallbackThreadInitializer(callback, callbackThreadInitializer);

```


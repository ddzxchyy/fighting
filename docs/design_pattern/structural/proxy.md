# 代理模式

问：代理模式是什么？

答：代理模式是一种结构型的设计模式。

问：代理模式有什么用？

答：在不改变原有类（被代理类）的情况下，通过引入代理类来给原有类附加功能。



## 静态代理

给 UserRepository 的方法添加方法耗时的功能。

```java
public interface UserRepository {
    
	UserEntity getUser(Integer uid);
}
```

在业务功能代码前后加上耗时功能的代码

```java
public class UserRepositoryImpl implements UserRepository {
	
    @Override
    public UserEntity getUser(Integer uid) {
        long startTime = System.currentTimeMillis();
        // 业务逻辑
        UserEntity userEntity = new UserEntity(1, "admin");
        long endTime = System.currentTimeMillis();
        System.out.println("用时：" + (endTime - startTime) + " ms");
        return userEntity;
    }
}
```
非业务功能代码入侵到业务代码中。

可以通过让 UserRepositoryImpl 处理只负责业务功能，  UserRepositoryProxy 负责附加功能并**委托**原始类执行业务代码。

```java
public class UserRepositoryProxy implements UserRepository {
    
    private final UserRepository userRepository;

    public UserRepositoryProxy(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getUser(Integer uid) {
        long startTime = System.currentTimeMillis();
        // 执行业务代码
        UserEntity userEntity = userRepository.getUser(uid);
        long endTime = System.currentTimeMillis();
        System.out.println("用时：" + (endTime - startTime) + " ms");
        return userEntity;
    }
}
```

```java
public class ProxyApp {
    
	public static void main(String[] args) {
        UserRepository userRepository = 
            new UserRepositoryProxy(new UserRepositoryImpl());
        System.out.println(userRepository.getUser(1));
    }
}
```

优点：业务代码和非业务代码分离，让业务类职责更加单一，专注于业务的实现。

缺点：

1. 需要实现被代理类的每个方法。

2. 其他类想要实现相同的功能，需要重新创建一个代理类。

## 动态代理

在运行的时候动态地创建原始类对应的代理类，然后在系统中用代理类替换掉原始类。

JAVA 语法支持动态代理。 通过动态代理返回的是接口，因为动态代理生成了一个新的类，不不是原有类，所以不能用 UserRepositoryImpl 去接。

```java
public class LogProxyHandler implements InvocationHandler {
    
    Object target;

    public LogProxyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = method.invoke(target, args);
        long endTime = System.currentTimeMillis();
        System.out.println("time: " + (endTime - startTime));
        return result;
    }
}
```



```java
public class ProxyApp {

    public static void main(String[] args) {
        UserRepository proxyUserRepository = (UserRepository) Proxy
                .newProxyInstance(UserRepositoryImpl.class.getClassLoader(),
                        UserRepositoryImpl.class.getInterfaces(),
                        new LogProxyHandler(new UserRepositoryImpl()));
        proxyUserRepository.getUser(1);
        boolean isInstanceof = proxyUserRepository instanceof UserRepositoryImpl;
        System.out.println("是否是 UserRepositoryImpl 的实例: " + isInstanceof);
    }
}

```


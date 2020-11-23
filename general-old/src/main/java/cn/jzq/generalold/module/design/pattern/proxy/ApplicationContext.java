package cn.jzq.generalold.module.design.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ApplicationContext {

    /**
     * 创建代理对象
     *
     * @param proxiedObject 被代理对象
     * @return 被
     */
    public Object createProxy(Object proxiedObject) {
//         第二个参数是接口的所有实现类的 class
//         the list of interfaces for the proxy class to implement
//        return Proxy.newProxyInstance(proxiedObject.getClass().getClassLoader(),
//                new Class[]{proxiedObject.getClass()},
//                new LogProxyHandler());
        return null;
    }
}


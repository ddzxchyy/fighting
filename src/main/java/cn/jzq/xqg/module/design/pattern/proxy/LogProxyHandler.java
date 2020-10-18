package cn.jzq.xqg.module.design.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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
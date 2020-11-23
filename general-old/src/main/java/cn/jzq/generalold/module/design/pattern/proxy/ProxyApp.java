package cn.jzq.generalold.module.design.pattern.proxy;

import cn.jzq.generalold.module.technology.spring.aop.UserRepository;
import cn.jzq.generalold.module.technology.spring.aop.UserRepositoryImpl;

import java.lang.reflect.Proxy;

/**
 * 代理
 */
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

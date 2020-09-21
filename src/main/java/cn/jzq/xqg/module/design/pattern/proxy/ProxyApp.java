package cn.jzq.xqg.module.design.pattern.proxy;

import cn.jzq.xqg.module.technology.spring.aop.UserController;
import cn.jzq.xqg.module.technology.spring.aop.UserEntity;
import cn.jzq.xqg.module.technology.spring.aop.UserRepository;
import cn.jzq.xqg.module.technology.spring.aop.UserRepositoryImpl;
import sun.rmi.log.LogHandler;

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

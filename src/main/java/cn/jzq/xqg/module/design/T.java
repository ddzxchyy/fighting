package cn.jzq.xqg.module.design;

import cn.jzq.xqg.module.technology.spring.aop.UserRepository;
import cn.jzq.xqg.module.technology.spring.aop.UserRepositoryImpl;
import cn.jzq.xqg.module.technology.spring.aop.UserService;
import cn.jzq.xqg.module.technology.spring.aop.UserServiceImpl;

public class T {
    private UserServiceImpl userService;

    public T(UserServiceImpl userService) {
        this.userService = userService;
    }
}

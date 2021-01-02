package cn.jzq.generalold.module.technology.spring.ioc;


import cn.jzq.generalold.module.technology.spring.aop.UserEntity;

public interface UserService {

    UserEntity getUser(Integer userId);
}

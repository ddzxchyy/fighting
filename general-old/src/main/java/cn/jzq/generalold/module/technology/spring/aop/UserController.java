package cn.jzq.generalold.module.technology.spring.aop;

public class UserController {

    public UserEntity getUser(Integer uid) {
        return new UserEntity(uid, "admin");
    }
}

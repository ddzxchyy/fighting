package cn.jzq.generalold.module.technology.spring.aop;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public UserEntity getUser(Integer uid) {
//        long startTime = System.currentTimeMillis();
        // 业务逻辑
        UserEntity userEntity = new UserEntity(1, "admin");
//        long endTime = System.currentTimeMillis();
//        System.out.println("用时：" + (endTime - startTime) + " ms");
        return userEntity;
    }

}

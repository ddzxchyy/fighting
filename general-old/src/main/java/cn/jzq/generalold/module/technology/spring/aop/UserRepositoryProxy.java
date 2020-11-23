package cn.jzq.generalold.module.technology.spring.aop;

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

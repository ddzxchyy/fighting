package cn.jzq.xqg.module.technology.spring.aop;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void save(UserEntity userEntity) {
        // 非业务代码
        System.out.println("开始事务");

        // 业务代码
        System.out.println("保存用户");

        System.out.println("提交");
    }

}

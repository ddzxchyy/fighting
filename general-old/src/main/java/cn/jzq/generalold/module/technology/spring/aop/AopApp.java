package cn.jzq.generalold.module.technology.spring.aop;

/**
 * aop
 *
 * @author jzq
 */
public class AopApp {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryProxy(new UserRepositoryImpl());
        System.out.println(userRepository.getUser(1));
    }
}

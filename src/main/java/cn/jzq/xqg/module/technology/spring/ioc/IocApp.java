package cn.jzq.xqg.module.technology.spring.ioc;

/**
 * Inversion of Control 控制反转
 * <p>
 * IOC 解决的是对象管理和对象依赖的问题
 * 依赖注入更多指的是实现 IOC 这个思想的实现方式：
 * 对象无需自己创建或管理它们的依赖关系，依赖关系将
 * 自动注入到需要它们的对象中去
 */
public class IocApp {

    public static void main(String[] args) {
        Duck duck = new Duck();

    }
}

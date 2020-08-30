package cn.jzq.xqg.module.technology.spring.ioc;

import cn.jzq.xqg.uitl.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Inversion of Control 控制反转
 * <p>
 * IOC 解决的是对象管理和对象依赖的问题
 * 依赖注入更多指的是实现 IOC 这个思想的实现方式：
 * 对象无需自己创建或管理它们的依赖关系，依赖关系将
 * 自动注入到需要它们的对象中去
 */
@RestController
@RequestMapping("/spring/ioc")
public class IocApp {

    @GetMapping("/user/{userId}")
    public R getUserInfo(@PathVariable("userId") Integer userId) {
       return R.ok();
    }
}

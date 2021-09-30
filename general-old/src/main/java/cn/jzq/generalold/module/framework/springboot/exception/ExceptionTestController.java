package cn.jzq.generalold.module.framework.springboot.exception;

import cn.jzq.fightingcommon.uitls.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test/exception")
@RestController
public class ExceptionTestController {

    @RequestMapping("/illegal")
    public R IllegalArgumentExceptionTest() {
        throw new IllegalArgumentException("参数错误");
    }

}

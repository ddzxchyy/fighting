package cn.jzq.generalold.module.framework.springboot.interceptor;

import cn.jzq.fightingcommon.uitls.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/interceptor")
public class InterceptorTestController {

    @GetMapping("")
    public R test() {
        return R.ok();
    }
}

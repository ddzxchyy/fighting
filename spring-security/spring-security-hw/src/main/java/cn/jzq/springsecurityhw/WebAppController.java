package cn.jzq.springsecurityhw;

import cn.jzq.fightingcommon.uitls.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WebAppController {

    @GetMapping("hello")
    public R helloWorld() {
        return R.ok("hello world ! hello security");
    }
}

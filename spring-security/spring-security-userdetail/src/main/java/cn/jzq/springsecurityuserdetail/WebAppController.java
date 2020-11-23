package cn.jzq.springsecurityuserdetail;

import cn.jzq.fightingcommon.uitls.R;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class WebAppController {

    @GetMapping("echo")
    public R helloWorld() {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return R.ok("echo");
    }
}

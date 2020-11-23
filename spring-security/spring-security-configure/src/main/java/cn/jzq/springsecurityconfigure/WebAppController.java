package cn.jzq.springsecurityconfigure;

import cn.jzq.fightingcommon.uitls.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class WebAppController {

    @GetMapping("echo")
    public R echo() {
        return R.ok("echo");
    }

    @GetMapping("/admin")
    public R admin() {
        return R.ok("admin");
    }

    @GetMapping("/normal")
    public R normal() {
        return R.ok("normal");
    }
}

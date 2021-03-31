package cn.jzq.springsecurityhw;

import cn.jzq.fightingcommon.uitls.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WebAppController {

    /**
     * 登录
     *
     * @param username 用户名|必填
     * @param password 密码
     * @return 当前登录用户的基本信息
     * @resp code 返回码(0000表示登录成功,其它表示失败)|string|必填
     * @resp msg 登录信息|string
     * @resp username 登录成功后返回的用户名|string
     */
    @ResponseBody
    @GetMapping("hello")
    public R helloWorld() {
        return R.ok("hello world ! hello security");
    }
}

package cn.jzq.generalold.module.framework.springboot.prop;

import cn.jzq.fightingcommon.uitls.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test/prop")
@RestController
@AllArgsConstructor
public class PropertiesTestController {

    private final ReadProperties readProperties;

    @GetMapping("/kotoba")
    public R listKotoba() {
        return R.ok(readProperties.getKotoba());
    }
}

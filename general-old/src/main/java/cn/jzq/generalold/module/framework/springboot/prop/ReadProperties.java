package cn.jzq.generalold.module.framework.springboot.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "mysoul")
public class ReadProperties {

    private List<Kotoba> kotoba;

    @Data
    static class Kotoba {
        private String name;

        private String description;
    }
}

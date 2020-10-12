# 读取配置

## 1. 简介

在项目中会将一些常用的配置信息比如七牛云 oss 配置、发送短信的相关信息配置等放到配置文件中。Spring 提供了几种读取方式，如 `@Value` 、`@ConfigurationProperties`。本文介绍 通过`@ConfigurationProperties` 方式读取配置文件的方法。

## 2. 快速入门

在 `application.yml` 中有如下配置：

```yml
mysoul:
  kotoba:
    - name: "kotoba"
      description: "隐约雷鸣 阴霾天空 但盼风雨来 能留你在此 隐约雷鸣 阴霾天空 即使天无雨 我亦留此地"
    - name: "rain"
      description: "我喜欢雨。因为它带来天空的味道。"
 
```



可以通过如下配置读取

```java
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
```



编写测试接口

```java
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

```



测试结果

```json
{
  "code": 0,
  "msg": "success",
  "data": [
    {
      "name": "kotoba",
      "description": "隐约雷鸣 阴霾天空 但盼风雨来 能留你在此 隐约雷鸣 阴霾天空 即使天无雨 我亦留此地"
    },
    {
      "name": "rain",
      "description": "我喜欢雨。因为它带来天空的味道。"
    }
  ]
}
```


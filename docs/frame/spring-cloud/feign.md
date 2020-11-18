# feign



## fallback

因为`Fallback`是通过`Hystrix`实现的， 所以需要开启`Hystrix`

```yml
feign:
  hystrix:
    enabled: true
```


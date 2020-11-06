# Spring Redis



## 自定义缓存失效时间

使用方式  `@Cacheable(cacheNames = "wgh-users#60",  key = "'xz::'+ #sysId")`

```java
package io.renren.config;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;

import java.time.Duration;

public class MyRedisCacheManager extends RedisCacheManager {

    public MyRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        String[] array = StringUtils.delimitedListToStringArray(name, "#");
        name = array[0];
        if (array.length > 1) { // 解析TTL
            long ttl = Long.parseLong(array[1]);
            cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(ttl)); // 注意单位我此处用的是秒，而非毫秒
        }
        return super.createRedisCache(name, cacheConfig);
    }



}
```



配置 RedisCacheManager

```java
@Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {

//         默认配置， 默认超时时间为5min
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration
                .ofMinutes(5L)).disableCachingNullValues();


        RedisCacheManager cacheManager =  new MyRedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(factory), defaultCacheConfig);

        return cacheManager;
    }
```

